import HttpService from "../services/HttpService";
import UserService from "../services/UserService";

const PROJECT_URL = "http://localhost:8080/api/user";

const UserApiService = {
  getUsers: () => {
    return HttpService.getClient().get(PROJECT_URL, {
        headers: {
          Authorization: `Bearer ${UserService.getToken()}`
        }
      })
      .then(response => {
        return response.data
      })
      .catch(error => {
        console.error("Error GetAllUsers:", error);
        throw error;
      })
  },
};

export default UserApiService;