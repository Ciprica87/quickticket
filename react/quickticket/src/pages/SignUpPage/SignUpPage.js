import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { signup } from '../../api/auth';
import SignUpForm from './SignUpForm';
import logo from '../../assets/logo.png';
import './SignUpPage.css';

const SignUpPage = () => {
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSignUp = async (userData) => {
    try {
      const response = await signup(userData);
      if (response.success) {
        navigate('/login');  
      }
    } catch (err) {
      setError(err.message || 'An error occurred during signup. Please try again.');
    }
  };

  return (
    <div className="signup-page">
      <img src={logo} className="logo" alt="logo" />
      <SignUpForm onSignUp={handleSignUp} />
      {error && <div className="error-message">{error}</div>}
    </div>
  );
};

export default SignUpPage;
