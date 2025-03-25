import React from 'react';
import { useNavigate } from 'react-router-dom';
import Container from '../../components/Container';
import Button from '../../components/Button';
import './LandingPage.css';
import logo from '../../assets/logo.png'; 

const LandingPage = () => {
  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate('/login');
  };

  const handleSignUpClick = () => {
    navigate('/signup'); 
  };

  return (
    <div className="landing-page">
      <img src={logo} className="logo" alt="logo" />
      <Container width="250px" height="auto">
        <div className="buttons-wrapper">
          <Button text="Login" onClick={handleLoginClick} />
          <Button text="Sign-Up" onClick={handleSignUpClick} /> 
          <Button text="Explore" />
        </div>
      </Container>
    </div>
  );
};

export default LandingPage;
