import React from 'react';
import './TabButton.css';

const TabButton = ({ text, onClick, className = '' }) => {
  return (
    <button className={`tab-btn ${className}`} onClick={onClick}>
      {text}
    </button>
  );
};

export default TabButton;
