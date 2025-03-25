import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ element: Component, isAuthenticated, role }) => {
  if (isAuthenticated === null) {
    return null; 
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (Component.name === 'EventManagerPage' && role !== 'ROLE_EventManager') {
    return <Navigate to="/login" replace />;
  }

  if (Component.name === 'SystemAdminPage' && role !== 'ROLE_SystemAdmin') {
    return <Navigate to="/login" replace />;
  }

  return <Component />;
};

export default PrivateRoute;
