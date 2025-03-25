import React, { useState, useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { BrowserRouter as Router, useRoutes } from 'react-router-dom';
import LandingPage from './pages/LandingPage/LandingPage';
import LoginPage from './pages/LoginPage/LoginPage';
import SignUpPage from './pages/SignUpPage/SignUpPage'; 
import EventManagerPage from './pages/EventManagerPage/EventManagerPage'; 
import SystemAdminPage from './pages/SystemAdminPage/SystemAdminPage'; 
import Footer from './components/Footer';
import PrivateRoute from './components/PrivateRoute';
import { getToken, getRole } from './api/tokenService';

const AppRoutes = ({ isAuthenticated, role, setIsAuthenticated, setRole }) => {
  return useRoutes([
    { path: "/", element: <LandingPage /> },
    { path: "/login", element: <LoginPage setIsAuthenticated={setIsAuthenticated} setRole={setRole} /> },
    { path: "/signup", element: <SignUpPage /> },
    { 
      path: "/manage", 
      element: <PrivateRoute element={EventManagerPage} isAuthenticated={isAuthenticated} role={role} /> 
    },
    { 
      path: "/admin", 
      element: <PrivateRoute element={SystemAdminPage} isAuthenticated={isAuthenticated} role={role} /> 
    },
    { path: "*", element: <Navigate to="/" replace /> } 
  ]);
};


function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(null); 
  const [role, setRole] = useState(null);

  useEffect(() => {
    const token = getToken();
    const userRole = getRole();

    if (token) {
      setIsAuthenticated(true);
      setRole(userRole);
    } else {
      setIsAuthenticated(false); 
    }
  }, []);

  if (isAuthenticated === null) {
    return null; 
  }

  return (
    <Router>
      <AppRoutes 
        isAuthenticated={isAuthenticated} 
        role={role} 
        setIsAuthenticated={setIsAuthenticated} 
        setRole={setRole} 
      />
      <Footer />
    </Router>
  );
}

export default App;

