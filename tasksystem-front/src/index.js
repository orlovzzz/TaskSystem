import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import UserService from "./services/UserService";

const renderApp = () => {
  const root = ReactDOM.createRoot(document.getElementById("root"));
  root.render(<App />);
};

UserService.initKeycloak(renderApp);