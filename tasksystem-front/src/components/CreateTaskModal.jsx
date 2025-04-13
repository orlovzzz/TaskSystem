import React, { useState, useEffect } from 'react';
import TaskService from '../api/taskApi';
import UserApiService from "../api/userApi";
import ProjectService from "../api/projectApi";
import { PRIORITIES } from '../constants/priorities';

const overlayStyle = {
  position: 'fixed',
  top: 0,
  left: 0,
  width: '100vw',
  height: '100vh',
  backgroundColor: 'rgba(0, 0, 0, 0.4)',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  zIndex: 1000,
};

const modalStyle = {
  backgroundColor: '#fff',
  borderRadius: '12px',
  width: '100%',
  maxWidth: '500px',
  boxShadow: '0 10px 25px rgba(0,0,0,0.2)',
  padding: '24px',
};

const formContainer = {
  display: 'flex',
  flexDirection: 'column',
  gap: '16px',
};

const labelStyle = {
  fontWeight: 'bold',
  color: '#111827',
  marginBottom: '8px',
};

const inputStyle = {
  marginLeft: '15px',
  padding: '8px',
  fontSize: '14px',
  borderRadius: '6px',
  border: '1px solid #ccc',
};

const buttonStyle = {
  padding: '10px 20px',
  backgroundColor: '#1e3a8a',
  color: '#fff',
  border: 'none',
  borderRadius: '8px',
  fontWeight: 'bold',
  cursor: 'pointer',
};

const closeButtonStyle = {
  backgroundColor: '#f3f4f6',
  color: '#111827',
  border: '1px solid #d1d5db',
  borderRadius: '6px',
  padding: '8px 12px',
  cursor: 'pointer',
  fontWeight: 'bold',
};

const buttonContainerStyle = {
  display: 'flex',
  gap: '16px',
  justifyContent: 'flex-end',
};

const userCheckboxStyle = {
  marginRight: '8px',
};

const CreateTaskModal = ({ projectId, majorTasks = [], task = null, onClose, onCreate }) => {
  const [name, setName] = useState(task ? task.name : '');
  const [description, setDescription] = useState(task ? task.description : '');
  const [assignee, setAssignee] = useState(task ? task.assignee : []);
  const [priority, setPriority] = useState(task ? task.priority : 'MEDIUM');
  const [majorTaskId, setMajorTaskId] = useState(task ? task.majorTask?.id : null);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    ProjectService.getProjectById(projectId)
      .then((project) => {
        setUsers(project.users);
      })
      .catch((err) => console.error('Ошибка при получении данных проекта:', err));
  }, [projectId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const taskData = {
      projectId,
      name,
      description,
      assignee,
      priority,
      majorTask: majorTaskId ? { id: majorTaskId } : null,
    };

    try {
      await TaskService.createTask(taskData);
      onCreate();
      onClose();
    } catch (err) {
      console.error('Ошибка при создании задачи:', err);
    }
  };

  const handleUserChange = (userId) => {
    if (assignee.includes(userId)) {
      setAssignee(assignee.filter(id => id !== userId));
    } else {
      setAssignee([...assignee, userId]);
    }
  };

  return (
    <div style={overlayStyle}>
      <div style={modalStyle}>
        <h2 style={{ fontSize: '20px', marginBottom: '16px', color: '#1e3a8a' }}>
          {task ? 'Редактирование задачи' : 'Создание задачи'}
        </h2>

        <form onSubmit={handleSubmit} style={formContainer}>
          <div>
            <label style={labelStyle}>Название</label>
            <input
              type="text"
              style={inputStyle}
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div>
            <label style={labelStyle}>Описание</label>
            <textarea
              style={inputStyle}
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              rows={4}
            />
          </div>

          <div>
            <label style={labelStyle}>Приоритет</label>
            <select
              style={inputStyle}
              value={priority}
              onChange={(e) => setPriority(e.target.value)}
            >
              {PRIORITIES.map((p) => (
                <option key={p} value={p}>{p}</option>
              ))}
            </select>
          </div>

          <div>
            <label style={labelStyle}>Исполнители</label>
            <div style={{ display: 'flex', flexDirection: 'column' }}>
              {users.map((user) => (
                <div key={user.login} style={{ display: 'flex', alignItems: 'center', marginBottom: '8px' }}>
                  <input
                    type="checkbox"
                    style={userCheckboxStyle}
                    checked={assignee.includes(user.login)}
                    onChange={() => handleUserChange(user.login)}
                  />
                  <span>{user.login}</span>
                </div>
              ))}
            </div>
          </div>

          <div>
            <label style={labelStyle}>Родительская задача</label>
            <select
              style={inputStyle}
              value={majorTaskId || ''}
              onChange={(e) => setMajorTaskId(e.target.value || null)}
            >
              <option value="">Нет</option>
              {majorTasks.map((task) => (
                <option key={task.id} value={task.id}>
                  {task.name}
                </option>
              ))}
            </select>
          </div>

          <div style={buttonContainerStyle}>
            <button type="button" onClick={onClose} style={closeButtonStyle}>
              Отмена
            </button>
            <button style={buttonStyle} type="submit">
              {task ? 'Обновить' : 'Создать'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateTaskModal;
