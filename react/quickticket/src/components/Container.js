import React from 'react';
import './Container.css';

const Container = ({ children, width = 'auto', height = 'auto', className = '' }) => {
  return (
    <div className={`container ${className}`} style={{ width, height }}>
      {children}
    </div>
  );
};

export default Container;
