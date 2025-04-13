import React, { useEffect, useState } from 'react';
import ProjectCard from '../components/ProjectCard';
import ProjectService from '../api/projectApi';
import EditProjectModal from '../components/EditProjectModal';
import CreateProjectModal from '../components/CreateProjectModal';

const pageStyle = {
  padding: '20px',
  fontFamily: 'Arial, sans-serif',
  backgroundColor: '#f3f4f6',
  minHeight: '100vh',
};

const headerStyle = {
  backgroundColor: '#1e3a8a',
  padding: '20px',
  borderRadius: '12px',
  color: 'white',
  textAlign: 'center',
  boxShadow: '0 4px 12px rgba(0, 0, 0, 0.15)',
  marginBottom: '32px',
  width: '95%',
  marginLeft: 'auto',
  marginRight: 'auto',
};

const titleStyle = {
  textAlign: 'center',
  fontSize: '32px',
  fontWeight: 'bold',
  marginBottom: '24px',
  color: '#ffffff',
};

const subtitleStyle = {
  fontSize: '24px',
  marginTop: '10px',
  color: '#ffffff',
};

const buttonStyle = {
  display: 'block',
  margin: '0 auto 32px',
  padding: '12px 20px',
  backgroundColor: '#2563eb',
  color: '#fff',
  fontSize: '16px',
  fontWeight: 'bold',
  borderRadius: '8px',
  border: 'none',
  cursor: 'pointer',
};

const cardSectionStyle = {
  backgroundColor: '#e0e7ff',
  padding: '24px',
  borderRadius: '12px',
  boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
  margin: '0 auto 40px',
  maxWidth: '95%',
};

const gridStyles = {
  display: 'flex',
  flexWrap: 'wrap',
  gap: '20px',
  justifyContent: 'center',
};

const ProjectsPage = () => {
  const [projects, setProjects] = useState([]);
  const [editingProject, setEditingProject] = useState(null);
  const [creating, setCreating] = useState(false);

  const fetchProjects = () => {
    ProjectService.getAllProjects()
      .then(setProjects)
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchProjects();
  }, []);

  const handleDelete = async (id) => {
    try {
      await ProjectService.deleteProject(id);
      fetchProjects();
    } catch (err) {
      console.error("Ошибка при удалении проекта:", err);
    }
  };

  return (
    <div style={pageStyle}>
      <div style={headerStyle}>
        <h1 style={titleStyle}>Task Management System</h1>
        <h2 style={subtitleStyle}>Проекты</h2>
      </div>

      <div style={cardSectionStyle}>
      <button style={buttonStyle} onClick={() => setCreating(true)}>Создать проект</button>
        <div style={gridStyles}>
          {projects.map(project => (
            <ProjectCard
              key={project.id}
              project={project}
              onEdit={() => setEditingProject(project)}
              onDelete={() => handleDelete(project.id)}
            />
          ))}
        </div>
      </div>

      {editingProject && (
        <EditProjectModal
          project={editingProject}
          onClose={() => setEditingProject(null)}
          onSuccess={() => {
            fetchProjects();
            setEditingProject(null);
          }}
        />
      )}

      {creating && (
        <CreateProjectModal
          onClose={() => setCreating(false)}
          onCreate={() => {
            fetchProjects();
            setCreating(false);
          }}
        />
      )}
    </div>
  );
};

export default ProjectsPage;
