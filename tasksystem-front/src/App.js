import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProjectsPage from "./pages/ProjectsPage";
import ProjectTasksPage from "./pages/ProjectTasksPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<ProjectsPage />} />
        <Route path="/project/:id" element={<ProjectTasksPage />} />
      </Routes>
    </Router>
  );
}

export default App;
