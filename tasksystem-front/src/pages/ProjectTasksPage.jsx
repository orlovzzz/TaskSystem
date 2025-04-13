import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import ProjectService from "../api/projectApi";
import TaskService from "../api/taskApi";
import CreateTaskModal from "../components/CreateTaskModal";
import TaskInfoModal from "../components/TaskInfoModal";

const pageStyle = {
  padding: "20px",
  fontFamily: "Arial, sans-serif",
  backgroundColor: "#f3f4f6",
  minHeight: "100vh",
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
};

const headerStyle = {
  backgroundColor: "#1e3a8a",
  padding: "20px",
  borderRadius: "12px",
  color: "white",
  textAlign: "center",
  boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
  marginBottom: "32px",
  width: "95%",
  position: "relative",
};

const backButtonStyle = {
  position: "absolute",
  left: "20px",
  top: "50%",
  transform: "translateY(-50%)",
  backgroundColor: "transparent",
  border: "none",
  color: "white",
  fontSize: "16px",
  fontWeight: "bold",
  cursor: "pointer",
};

const mainContainerStyle = {
  backgroundColor: "#e0e7ff",
  padding: "24px",
  borderRadius: "12px",
  boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
  margin: "0 auto 40px",
  maxWidth: "95%",
  width: "95%",
  display: "flex",
  flexDirection: "column",
  gap: "20px",
};

const buttonContainerStyle = {
  display: "flex",
  justifyContent: "center",
};

const buttonStyle = {
  backgroundColor: "#2563eb",
  color: "white",
  padding: "10px 20px",
  border: "none",
  borderRadius: "8px",
  fontWeight: "bold",
  cursor: "pointer",
};

const kanbanContainerStyle = {
  display: "flex",
  gap: "20px",
  justifyContent: "space-between",
};

const columnStyle = {
  flex: 1,
  backgroundColor: "#ffffff",
  borderRadius: "12px",
  padding: "16px",
  boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
  minHeight: "300px",
};

const columnTitleStyle = {
  textAlign: "center",
  fontWeight: "bold",
  fontSize: "18px",
  marginBottom: "12px",
};

const taskCardStyle = {
  backgroundColor: "#fff",
  padding: "16px",
  borderRadius: "10px",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
  marginBottom: "12px",
  display: "flex",
  flexDirection: "column",
  gap: "8px",
  width: "100%",
  textAlign: "left",
  cursor: "pointer",
  border: "none",
};

const taskTitleStyle = {
  fontSize: "16px",
  fontWeight: "bold",
  color: "#1f2937",
};

const taskIdStyle = {
  fontSize: "14px",
  color: "#6b7280",
};

const getPriorityColor = (priority) => {
  switch (priority) {
    case "HIGH":
      return "#ef4444";
    case "MEDIUM":
      return "#f59e0b";
    case "LOW":
      return "#10b981";
    default:
      return "#9ca3af";
  }
};

const ProjectTaskPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const projectId = parseInt(id);
  const [project, setProject] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);

  const fetchProject = async () => {
    try {
      const data = await ProjectService.getProjectById(projectId);
      setProject(data);
    } catch (err) {
      console.error("Ошибка загрузки проекта:", err);
    }
  };

  const fetchProjectTasks = async () => {
    try {
      const data = await TaskService.getTasksByProjectId(projectId);
      setTasks(data || []);
    } catch (err) {
      console.error("Ошибка загрузки задач:", err);
    }
  };

  useEffect(() => {
    fetchProject();
    fetchProjectTasks();
  }, [projectId]);

  const groupedTasks = {
    OPEN: [],
    IN_PROGRESS: [],
    CLOSED: [],
  };

  tasks.forEach((task) => {
    groupedTasks[task.status]?.push(task);
  });

  return (
    <div style={pageStyle}>
      <div style={headerStyle}>
        <button style={backButtonStyle} onClick={() => navigate("/")}>На главную</button>
        <h1>{project?.name || "Загрузка проекта..."}</h1>
        <p>Kanban доска</p>
      </div>

      <div style={mainContainerStyle}>
        <div style={buttonContainerStyle}>
          <button style={buttonStyle} onClick={() => setShowCreateModal(true)}>Создать задачу</button>
        </div>

        <div style={kanbanContainerStyle}>
          {Object.entries(groupedTasks).map(([statusKey, taskList]) => (
            <div key={statusKey} style={columnStyle}>
              <div style={columnTitleStyle}>
                {statusKey === "OPEN" && "Открытые"}
                {statusKey === "IN_PROGRESS" && "В процессе"}
                {statusKey === "CLOSED" && "Завершённые"}
              </div>

              {taskList.map((task) => (
                <button
                  key={task.id}
                  style={taskCardStyle}
                  onClick={() => setSelectedTask(task)}
                >
                  <div style={taskIdStyle}>{project?.name}-{task.id}</div>
                  <div style={taskTitleStyle}>{task.name}</div>
                  <div
                    style={{
                      fontSize: "13px",
                      fontWeight: "bold",
                      color: getPriorityColor(task.priority),
                    }}
                  >
                    {task.priority}
                  </div>
                </button>
              ))}
            </div>
          ))}
        </div>
      </div>

      {showCreateModal && (
        <CreateTaskModal
          onClose={() => setShowCreateModal(false)}
          onCreate={() => {
            fetchProjectTasks();
            setShowCreateModal(false);
          }}
          projectId={projectId}
          majorTasks={tasks}
        />
      )}

      {selectedTask && (
        <TaskInfoModal
          task={selectedTask}
          projectName={project?.name}
          onClose={() => setSelectedTask(null)}
          onDelete={() => fetchProjectTasks()}
        />
      )}
    </div>
  );
};

export default ProjectTaskPage;
