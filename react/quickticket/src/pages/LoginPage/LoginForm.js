import React, { useState } from 'react';
import Button from '../../components/Button';
import { useNavigate } from 'react-router-dom'; 
import './LoginForm.css';

const LoginForm = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); 

  const handleLogin = (e) => {
    e.preventDefault();
    onLogin(username, password);
  };

  const handleSignup = () => {
    navigate('/signup'); 
  };

  return (
    <form className="login-form" onSubmit={handleLogin}>
      <h2>Please enter your Credentials:</h2>
      <div className="login-form-group">
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
      </div>
      <div className="login-form-group">
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
      </div>
      <Button text="Login" type="submit" />
      <Button text="Signup" type="button" onClick={handleSignup} /> 
    </form>
  );
};

export default LoginForm;
