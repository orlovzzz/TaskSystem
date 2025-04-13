import HttpService from "../services/HttpService";
import UserService from "../services/UserService";

const TASK_URL = "http://localhost:8082/api/task";

const TaskService = {
  getTasksByProjectId: (id) => {
    return HttpService.getClient().get(TASK_URL, {
      params: {
        projectId: id,
      },
      headers: {
        Authorization: `Bearer ${UserService.getToken()}`
      }
      })
      .then(response => {
        return response.data
      })
      .catch(error => {
        console.error("Error GetTasks:", error);
        throw error;
      })
  },

  createTask: (task) => {
    return HttpService.getClient().post(TASK_URL, task, {
        headers: {
          Authorization: `Bearer ${UserService.getToken()}`
        }
      })
      .then(response => {
        return response.data
      })
      .catch(error => {
        console.error("Error CreateTask:", error);
        throw error;
      })
  },

  deleteTask: (id) => {
    return HttpService.getClient().delete(`${TASK_URL}/${id}`, {
      headers: {
        Authorization: `Bearer ${UserService.getToken()}`
      }
    }).catch(error => {
      console.error("Error DeleteTask:", error);
      throw error;
    })
  },

  updateTask: (id, task) => {
    return HttpService.getClient().put(`${TASK_URL}/${id}`, task, {
      headers: {
        Authorization: `Bearer ${UserService.getToken()}`
      }
    })
  }
};

export default TaskService;