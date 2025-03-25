import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../../api/auth';
import LoginForm from './LoginForm';
import logo from '../../assets/logo.png';
import './LoginPage.css';

const LoginPage = ({ setIsAuthenticated, setRole }) => {
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (username, password) => {
    try {
      const response = await login(username, password);
      if (response.success) {
        console.log('Login successful:', response); 
        const role = sessionStorage.getItem('role');
        console.log('User role:', role); 
        
        setIsAuthenticated(true);
        setRole(role);

        if (role === 'ROLE_SystemAdmin') {
          navigate('/admin');
        } else if (role === 'ROLE_EventManager') {
          navigate('/manage');
        } else if (role === 'ROLE_Staff') {
          navigate('/staff');
        } else {
          navigate('/dashboard'); 
        }
      } else {
        setError('Login failed. Please check your credentials.');
      }
    } catch (err) {
      console.error('Login error:', err.message); 
      setError(err.message || 'An error occurred during login. Please try again.');
    }
  };

  return (
    <div className="login-page">
      <img src={logo} className="logo" alt="logo" />
      <LoginForm onLogin={handleLogin} />
      {error && <div className="error-message">{error}</div>}
    </div>
  );
};

export default LoginPage;
