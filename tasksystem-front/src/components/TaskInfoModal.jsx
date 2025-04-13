import React, {useEffect, useState} from "react";
import { Edit, Trash } from "react-feather";
import TaskService from "../api/taskApi";
import EditTaskModal from "./EditTaskModal";

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
  width: "400px",
  maxWidth: "90%",
  boxShadow: "0 6px 18px rgba(0,0,0,0.2)",
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
  height: "auto",
  position: "relative",
};

const modalHeaderStyle = {
  display: "flex",
  flexDirection: "column",
  marginBottom: "20px",
};

const taskTitleStyle = {
  fontSize: "20px",
  fontWeight: "bold",
  color: "#1f2937",
};

const closeButtonStyle = {
  backgroundColor: "#ef4444",
  color: "white",
  border: "none",
  borderRadius: "6px",
  padding: "8px 12px",
  cursor: "pointer",
  marginTop: "20px",
  alignSelf: "center",
};

const baseActionButtonStyle = {
  background: "transparent",
  border: "none",
  cursor: "pointer",
  position: "absolute",
  top: "12px",
};

const editButtonStyle = {
  ...baseActionButtonStyle,
  right: "48px",
};

const deleteButtonStyle = {
  ...baseActionButtonStyle,
  right: "12px",
};

const TaskInfoModal = ({ task, projectName, onClose, onDelete }) => {
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [currentTask, setCurrentTask] = useState(task);

  const handleEditClick = () => {
    setIsEditModalOpen(true);
  };

  const handleEditClose = () => {
    setIsEditModalOpen(false);
  };

  const handleUpdateTask = (updatedTask) => {
    setCurrentTask(updatedTask);
  };

  const onTaskDelete = () => {
    TaskService.deleteTask(task.id)
      .then(() => {
        onDelete();
        onClose();
      })
      .catch((err) => {
        console.error(err);
      });
  };

  useEffect(() => {
    setCurrentTask(task);
  }, [task]);

  return (
    <div style={modalOverlayStyle} onClick={onClose}>
      <div style={modalContentStyle} onClick={(e) => e.stopPropagation()}>
        {/* Иконки для изменения и удаления */}
        <button
          style={editButtonStyle}
          onClick={handleEditClick}
          title="Изменить задачу"
        >
          <Edit size={20} />
        </button>
        <button
          style={deleteButtonStyle}
          onClick={onTaskDelete}
          title="Удалить задачу"
        >
          <Trash size={20} />
        </button>

        <div style={modalHeaderStyle}>
          <h2 style={taskTitleStyle}>{projectName}-{currentTask.id}</h2>
          <p><strong>Название:</strong> {currentTask.name}</p>
          <p><strong>Создатель:</strong> {currentTask.owner}</p>
          <p><strong>Описание:</strong> {currentTask.description || "—"}</p>
          <p><strong>Приоритет:</strong> {currentTask.priority}</p>
          <p><strong>Исполнители:</strong> {Array.isArray(currentTask.assignee) ? currentTask.assignee.join(", ") : currentTask.assignee || "—"}</p>
          <p><strong>Статус:</strong> {currentTask.status === "IN_PROGRESS" ? "IN PROGRESS" : currentTask.status}</p>
        </div>

        <button style={closeButtonStyle} onClick={onClose}>
          Закрыть
        </button>
      </div>

      {/* Окно редактирования задачи */}
      {isEditModalOpen && (
        <EditTaskModal
          task={currentTask}
          onClose={handleEditClose}
          onUpdate={handleUpdateTask}
        />
      )}
    </div>
  );
};

export default TaskInfoModal;