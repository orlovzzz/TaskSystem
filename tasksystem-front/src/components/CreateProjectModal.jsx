import React, { useState, useEffect } from 'react';
import UserApiService from "../api/userApi";
import ProjectService from "../api/projectApi";

const modalStyle = {
  position: 'fixed',
  top: '0',
  left: '0',
  right: '0',
  bottom: '0',
  backgroundColor: 'rgba(0, 0, 0, 0.5)',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
};

const modalContentStyle = {
  backgroundColor: '#fff',
  padding: '20px',
  borderRadius: '8px',
  maxWidth: '600px',
  width: '100%',
};

const inputStyle = {
  marginBottom: '12px',
  padding: '8px',
  border: '1px solid #ccc',
  borderRadius: '4px',
  width: '95%',
};

const roleInputStyle = {
  marginBottom: '12px',
  padding: '8px',
  border: '1px solid #ccc',
  borderRadius: '4px',
  width: '50%',
};

const userSelectStyle = {
  display: 'flex',
  alignItems: 'center',
  marginBottom: '12px',
};

const buttonStyle = {
  backgroundColor: '#4CAF50',
  color: '#fff',
  padding: '10px 20px',
  border: 'none',
  borderRadius: '4px',
  cursor: 'pointer',
};

const CreateProjectModal = ({ onClose, onCreate }) => {
  const [projectName, setProjectName] = useState('');
  const [projectDescription, setProjectDescription] = useState('');
  const [selectedUsers, setSelectedUsers] = useState({});
  const [roles, setRoles] = useState({});
  const [permissions, setPermissions] = useState({});
  const [users, setUsers] = useState([]);

  const fetchUsers = () => {
    UserApiService.getUsers()
      .then(setUsers)
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleUserSelect = (userLogin, isSelected, role, permission) => {
    setSelectedUsers((prevSelectedUsers) => {
      const updated = { ...prevSelectedUsers };
      if (isSelected) {
        updated[userLogin] = { role, permission };
      } else {
        delete updated[userLogin];
      }
      return updated;
    });
  };

  const handleSubmit = () => {
    if (!projectName || !projectDescription || Object.keys(selectedUsers).length === 0) {
      alert("Пожалуйста, заполните все поля и выберите хотя бы одного пользователя.");
      return;
    }

    const createProject = {
      name: projectName,
      description: projectDescription,
      users: Object.keys(selectedUsers).map((login) => ({
        login,
        role: selectedUsers[login].role,
        permission: selectedUsers[login].permission,
      })),
    };

    ProjectService.createProject(createProject).then(() => onCreate());
    };

  return (
    <div style={modalStyle}>
      <div style={modalContentStyle}>
        <h2>Создание нового проекта</h2>

        <div style={{ marginBottom: '16px' }}>
          <label>Название проекта</label>
          <input
            type="text"
            value={projectName}
            onChange={(e) => setProjectName(e.target.value)}
            style={inputStyle}
          />
        </div>

        <div style={{ marginBottom: '16px' }}>
          <label>Описание проекта</label>
          <textarea
            value={projectDescription}
            onChange={(e) => setProjectDescription(e.target.value)}
            style={inputStyle}
          />
        </div>

        <h3>Выберите пользователей</h3>
        {users && users.length > 0 ? (
          users.map((user) => (
            <div key={user.login} style={userSelectStyle}>
              <label>
                <input
                  type="checkbox"
                  onChange={(e) => handleUserSelect(user.login, e.target.checked, roles[user.login], permissions[user.login])}
                />
                {user.login || "Неизвестный логин"}
              </label>

              {selectedUsers[user.login] && (
                <>
                  <input
                    type="text"
                    placeholder="Роль"
                    value={roles[user.login] || ''}
                    onChange={(e) => {
                      setRoles((prevRoles) => ({
                        ...prevRoles,
                        [user.login]: e.target.value,
                      }));
                      handleUserSelect(user.login, true, e.target.value, permissions[user.login]);
                    }}
                    style={roleInputStyle}
                  />

                  <select
                    value={permissions[user.login] || 'USER'}
                    onChange={(e) => {
                      setPermissions((prevPermissions) => ({
                        ...prevPermissions,
                        [user.login]: e.target.value,
                      }));
                      handleUserSelect(user.login, true, roles[user.login], e.target.value);
                    }}
                    style={userSelectStyle}
                  >
                    <option value="USER">User</option>
                    <option value="ADMIN">Admin</option>
                  </select>
                </>
              )}
            </div>
          ))
        ) : (
          <p>Нет пользователей для отображения</p>
        )}

        <div style={{ marginTop: '20px', textAlign: 'center' }}>
          <button style={buttonStyle} onClick={handleSubmit}>
            Создать проект
          </button>
          <button
            style={{
              ...buttonStyle,
              backgroundColor: '#f44336',
              marginLeft: '10px',
            }}
            onClick={onClose}
          >
            Закрыть
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreateProjectModal;
