import HttpService from "../services/HttpService";
import UserService from "../services/UserService";

const PROJECT_URL = "http://localhost:8081/api/project";

const ProjectService = {
  getAllProjects: () => {
    return HttpService.getClient().get(PROJECT_URL, {
      headers: {
        Authorization: `Bearer ${UserService.getToken()}`
      }
      })
      .then(response => {
        return response.data
      })
      .catch(error => {
        console.error("Error GetAllProjects:", error);
        throw error;
      })
  },

  getProjectById: (id) => {
    return HttpService.getClient().get(`${PROJECT_URL}/${id}`, {
        headers: {
          Authorization: `Bearer ${UserService.getToken()}`
        }
      })
      .then(response => {
        return response.data
      })
      .catch(error => {
        console.error("Error GetProjectById:", error);
        throw error;
      })
  },

  updateProject: (id, project) => {
    return HttpService.getClient().put(`${PROJECT_URL}/${id}`, project, {
      headers: {
        Authorization: `Bearer ${UserService.getToken()}`
      }
    }).catch(error => {
      console.error("Error UpdateProject:", error);
      throw error;
    })
  },

  createProject: (project) => {
    return HttpService.getClient().post(`${PROJECT_URL}`, project, {
      headers: {
        Authorization: `Bearer ${UserService.getToken()}`
      }
    }).catch(error => {
      console.error("Error CreateProject:", error);
      throw error;
    })
  },

  deleteProject: (id) => {
    return HttpService.getClient().delete(`${PROJECT_URL}/${id}`, {
      headers: {
        Authorization: `Bearer ${UserService.getToken()}`
      }
    }).catch(error => {
      console.error("Error DeleteProject:", error);
      throw error;
    })
  },
};

export default ProjectService;