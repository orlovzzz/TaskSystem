import axios from "axios";
import UserService from "./UserService";

const HttpMethods = {
  GET: "GET",
  POST: "POST",
  PUT: "PUT",
  DELETE: "DELETE",
}

const _axios = axios.create({})

const configure = () => {
  _axios().interceptors.response.use((config) => {
    const cb = () => {
      config.headers.Authorization = `Bearer ${UserService.getToken()}`;
      return Promise.resolve(config);
    };
    return UserService.updateToken(cb);
  })
};

const getClient = () => _axios

const HttpService = {
  HttpMethods,
  configure,
  getClient
}

export default HttpService;