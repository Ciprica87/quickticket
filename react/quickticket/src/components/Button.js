import React from 'react';
import './Button.css';

const Button = ({ text, onClick, type = 'button', className }) => {
  return (
    <button className={`btn ${className}`} onClick={onClick} type={type}>
      {text}
    </button>
  );
};

export default Button;
