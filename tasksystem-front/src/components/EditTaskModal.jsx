import React, { useState, useEffect } from "react";
import TaskService from "../api/taskApi";
import ProjectService from "../api/projectApi";
import {useParams} from "react-router-dom";

const modalOverlayStyle = {
  position: "fixed",
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  backgroundColor: "rgba(0,0,0,0.5)",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  zIndex: 1000,
};

const modalContentStyle = {
  backgroundColor: "white",
  padding: "24px",
  borderRadius: "12px",
  width: "500px",  // Увеличиваем ширину модального окна
  maxWidth: "90%",
  boxShadow: "0 6px 18px rgba(0,0,0,0.2)",
};

const closeButtonStyle = {
  backgroundColor: "#ef4444",
  color: "white",
  border: "none",
  borderRadius: "6px",
  padding: "8px 12px",
  cursor: "pointer",
  float: "right",
};

const EditTaskModal = ({ task, onClose, onUpdate }) => {
  const { id } = useParams();
  const [formData, setFormData] = useState({
    name: task?.name || "",
    description: task?.description || "",
    priority: task?.priority || "LOW",
    assignee: task?.assignee || [],
    status: task?.status || "OPEN",
  });
  const [projectUsers, setProjectUsers] = useState([]);

  const fetchProject = async () => {
    try {
      const data = await ProjectService.getProjectById(id);
      setProjectUsers(data.users);
    } catch (err) {
      console.error("Ошибка загрузки проекта:", err);
    }
  };

  useEffect(() => {
    fetchProject();
  }, [id]);

  useEffect(() => {
    setFormData({
      name: task?.name || "",
      description: task?.description || "",
      priority: task?.priority || "LOW",
      assignee: task?.assignee || [],
      status: task?.status || "OPEN",
    });
  }, [task]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleAssigneeChange = (e, uLogin) => {
    const isChecked = e.target.checked;
    setFormData((prevState) => {
      const newAssignees = isChecked
        ? [...prevState.assignee, uLogin]
        : prevState.assignee.filter((login) => login !== uLogin);
      return { ...prevState, assignee: newAssignees };
    });
  };

  const handleSubmit = () => {
    const updatedTask = { ...task, ...formData };
    console.log(updatedTask)

    TaskService.updateTask(updatedTask.id, updatedTask)
      .then(() => {
        onUpdate(updatedTask);
        onClose();
      })
      .catch((err) => {
        console.error("Ошибка обновления задачи:", err);
      });
  };

  return (
    <div style={modalOverlayStyle} onClick={onClose}>
      <div style={modalContentStyle} onClick={(e) => e.stopPropagation()}>
        <button style={closeButtonStyle} onClick={onClose}>Закрыть</button>
        <h2>Редактировать задачу</h2>
        <div>
          <label>Название:</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            placeholder="Название задачи"
          />
        </div>
        <div>
          <label>Описание:</label>
          <textarea
            name="description"
            value={formData.description}
            onChange={handleInputChange}
            placeholder="Описание задачи"
          />
        </div>
        <div>
          <label>Приоритет:</label>
          <select
            name="priority"
            value={formData.priority}
            onChange={handleInputChange}
          >
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
          </select>
        </div>
        <div>
          <label>Статус:</label>
          <select
            name="status"
            value={formData.status}
            onChange={handleInputChange}
          >
            <option value="OPEN">Open</option>
            <option value="IN_PROGRESS">In progress</option>
            <option value="CLOSED">Closed</option>
          </select>
        </div>
        <div>
          <label>Исполнители:</label>
          <div>
            {projectUsers.map((user) => (
              <div key={user.login}>
                <input
                  type="checkbox"
                  checked={formData.assignee.includes(user.login)}
                  onChange={(e) => handleAssigneeChange(e, user.login)}
                />
                <label>{user.login}</label>  {}
              </div>
            ))}
          </div>
        </div>
        <div style={{ marginTop: "20px" }}>
          <button onClick={handleSubmit}>Сохранить изменения</button>
        </div>
      </div>
    </div>
  );
};

export default EditTaskModal;
