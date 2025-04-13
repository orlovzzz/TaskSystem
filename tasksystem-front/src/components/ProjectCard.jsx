import React, { useState } from "react";
import { Users, UserCircle2, Pencil, Trash2 } from "lucide-react";
import { useNavigate } from "react-router-dom";

const cardStyle = {
  width: "100%",
  maxWidth: "400px",
  borderRadius: "16px",
  border: "1px solid #cfe2ff",
  background: "linear-gradient(135deg, #eff6ff, #ffffff, #f3e8ff)",
  padding: "24px",
  boxShadow: "0 4px 10px rgba(0, 0, 0, 0.1)",
  transition: "all 0.2s ease-in-out",
  position: "relative",
};

const cardHoverStyle = {
  ...cardStyle,
  transform: "scale(1.01)",
  boxShadow: "0 6px 15px rgba(0, 0, 0, 0.15)",
};

const headerStyle = {
  display: "flex",
  justifyContent: "space-between",
  marginBottom: "16px",
};

const titleStyle = {
  fontSize: "24px",
  fontWeight: "bold",
  color: "#1e3a8a",
};

const descriptionStyle = {
  width: "100%",
  fontSize: "14px",
  color: "#374151",
  marginBottom: "16px",
  whiteSpace: "normal",
  wordWrap: "break-word",
};

const footerStyle = {
  display: "flex",
  justifyContent: "space-between",
  fontSize: "14px",
};

const leftBlock = {
  display: "flex",
  alignItems: "center",
  gap: "8px",
  color: "#1d4ed8",
  fontWeight: 500,
};

const rightBlock = {
  display: "flex",
  alignItems: "center",
  gap: "8px",
  color: "#047857",
};

const actionButtons = {
  position: "absolute",
  top: "16px",
  right: "16px",
  display: "flex",
  gap: "8px",
};

const iconButtonStyle = {
  background: "none",
  border: "none",
  cursor: "pointer",
  color: "#6b7280",
  padding: "4px",
  transition: "color 0.2s ease-in-out",
};

const goButtonStyle = {
  marginTop: "16px",
  backgroundColor: "#1e3a8a",
  color: "white",
  padding: "10px 16px",
  borderRadius: "8px",
  border: "none",
  cursor: "pointer",
  fontSize: "14px",
  fontWeight: "bold",
  width: "100%",
};

const ProjectCard = ({ project, onEdit, onDelete }) => {
  const [isHovered, setIsHovered] = useState(false);
  const navigate = useNavigate();

  const goToProject = () => {
    navigate(`/project/${project.id}`, { state: { project } });
  };

  return (
    <div
      style={isHovered ? cardHoverStyle : cardStyle}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div style={actionButtons}>
        <button
          style={iconButtonStyle}
          title="Редактировать"
          onClick={() => onEdit(project)}
        >
          <Pencil size={18} />
        </button>
        <button
          style={iconButtonStyle}
          title="Удалить"
          onClick={() => onDelete(project.id)}
        >
          <Trash2 size={18} />
        </button>
      </div>

      <div style={headerStyle}>
        <h2 style={titleStyle}>{project.name}</h2>
      </div>

      <p style={descriptionStyle}>{project.description}</p>

      <div style={footerStyle}>
        <div style={leftBlock}>
          <UserCircle2 size={20} />
          <span>{project.owner}</span>
        </div>
        <div style={rightBlock}>
          <Users size={20} />
          <span>
            {(project.users?.length || 1)} участник
            {(project.users?.length === 1) ? "" : "ов"}
          </span>
        </div>
      </div>

      <button style={goButtonStyle} onClick={goToProject}>
        Перейти в проект
      </button>
    </div>
  );
};

export default ProjectCard;
