import React, { useEffect, useState } from 'react';
import ProjectService from '../api/projectApi';
import UserApiService from '../api/userApi';

const modalOverlayStyle = {
  position: 'fixed',
  top: 0,
  left: 0,
  width: '100vw',
  height: '100vh',
  backgroundColor: 'rgba(0,0,0,0.4)',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  zIndex: 1000,
};

const modalStyle = {
  backgroundColor: '#fff',
  padding: '32px',
  borderRadius: '12px',
  width: '90%',
  maxWidth: '600px',
  boxShadow: '0 10px 25px rgba(0,0,0,0.2)',
};

const inputStyle = {
  width: '95%',
  padding: '10px 14px',
  marginBottom: '16px',
  borderRadius: '8px',
  border: '1px solid #d1d5db',
  fontSize: '15px',
};

const buttonGroup = {
  display: 'flex',
  justifyContent: 'flex-end',
  gap: '12px',
};

const buttonStyle = {
  padding: '10px 16px',
  borderRadius: '8px',
  border: 'none',
  fontSize: '14px',
  cursor: 'pointer',
};

const userSelectStyle = {
  display: 'flex',
  alignItems: 'center',
  marginBottom: '12px',
  gap: '10px',
};

const labelStyle = {
  minWidth: '100px',
};

const EditProjectModal = ({ project, onClose, onSuccess }) => {
  const [form, setForm] = useState({
    name: project.name,
    description: project.description,
    owner: project.owner,
    users: project.users || [],
  });

  const [allUsers, setAllUsers] = useState([]);
  const [roles, setRoles] = useState({});
  const [permissions, setPermissions] = useState({});
  const [selectedUsers, setSelectedUsers] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    UserApiService.getUsers()
      .then((res) => {
        setAllUsers(res);
        const initialSelected = {};
        const initialRoles = {};
        const initialPermissions = {};
        project.users.forEach((u) => {
          initialSelected[u.login] = true;
          initialRoles[u.login] = u.role;
          initialPermissions[u.login] = u.permission;
        });
        setSelectedUsers(initialSelected);
        setRoles(initialRoles);
        setPermissions(initialPermissions);
      })
      .catch(console.error);
  }, [project.users]);

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleUserToggle = (login) => {
    setSelectedUsers((prev) => ({
      ...prev,
      [login]: !prev[login],
    }));
  };

  const handleSubmit = () => {
    const updatedUsers = Object.keys(selectedUsers)
      .filter((login) => selectedUsers[login])
      .map((login) => ({
        login,
        role: roles[login] || 'member',
        permission: permissions[login] || 'USER',
      }));

    const updatedForm = {
      ...form,
      users: updatedUsers,
    };

    setLoading(true);
    ProjectService.updateProject(project.id, updatedForm)
      .then((res) => {
        onSuccess(res.data);
        onClose();
      })
      .catch((err) => {
        console.error('Ошибка при обновлении проекта:', err);
        alert('Не удалось сохранить проект.');
      })
      .finally(() => setLoading(false));
  };

  return (
    <div style={modalOverlayStyle} onClick={onClose}>
      <div style={modalStyle} onClick={(e) => e.stopPropagation()}>
        <h2 style={{ marginBottom: '20px' }}>Редактировать проект</h2>
        <input
          style={inputStyle}
          name="name"
          value={form.name}
          onChange={handleChange}
          placeholder="Название проекта"
        />
        <input
          style={inputStyle}
          name="description"
          value={form.description}
          onChange={handleChange}
          placeholder="Описание"
        />

        <h3>Участники проекта</h3>
        {allUsers.map((user) => (
          <div key={user.login} style={userSelectStyle}>
            <input
              type="checkbox"
              checked={selectedUsers[user.login] || false}
              onChange={() => handleUserToggle(user.login)}
            />
            <span style={labelStyle}>{user.login}</span>
            <input
              type="text"
              placeholder="Роль"
              value={roles[user.login] || ''}
              onChange={(e) =>
                setRoles((prev) => ({ ...prev, [user.login]: e.target.value }))
              }
              disabled={!selectedUsers[user.login]}
              style={{ ...inputStyle, width: '120px', marginBottom: 0 }}
            />
            <select
              value={permissions[user.login] || 'USER'}
              onChange={(e) => {
                setPermissions((prevPermissions) => ({
                  ...prevPermissions,
                  [user.login]: e.target.value,
                }));
              }}
              style={userSelectStyle}
            >
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
            </select>

          </div>
        ))}

        <div style={buttonGroup}>
          <button style={{ ...buttonStyle, backgroundColor: '#e5e7eb' }} onClick={onClose}>
            Отмена
          </button>
          <button
            style={{
              ...buttonStyle,
              backgroundColor: '#4f46e5',
              color: '#fff',
              opacity: loading ? 0.6 : 1,
              pointerEvents: loading ? 'none' : 'auto',
            }}
            onClick={handleSubmit}
          >
            {loading ? 'Сохраняю...' : 'Сохранить'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default EditProjectModal;
