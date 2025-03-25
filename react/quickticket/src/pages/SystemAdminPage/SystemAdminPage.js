import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 
import Button from '../../components/Button';
import EventsAdminPanel from './EventsAdminPanel';
import UsersAdminPanel from './UsersAdminPanel';
import Modal from '../../components/Modal'; 
import './SystemAdminPage.css';

const SystemAdminPage = () => {
  const [view, setView] = useState('events'); 
  const [isLogoutModalOpen, setIsLogoutModalOpen] = useState(false); 
  const navigate = useNavigate(); 

  const handleLogout = () => {
    setIsLogoutModalOpen(true); 
  };

  const confirmLogout = () => {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userId');
    navigate('/login');
  };

  const cancelLogout = () => {
    setIsLogoutModalOpen(false); 
  };

  const renderContent = () => {
    if (view === 'events') {
      return <EventsAdminPanel />;
    } else if (view === 'users') {
      return <UsersAdminPanel />;
    }
  };

  return (
    <div className="system-admin-page">
      <div className="top-bar">
        <h2>System Administration</h2>
        <div className="button-group">
          <Button
            text={view === 'events' ? 'Switch to Users' : 'Switch to Events'}
            onClick={() => setView(view === 'events' ? 'users' : 'events')}
            className="switch-view-btn"
          />
          <Button
            text="Log Out"
            onClick={handleLogout} 
            className="logout-btn"
          />
        </div>
      </div>
      <div className="content">{renderContent()}</div>

      <Modal isOpen={isLogoutModalOpen} onClose={cancelLogout}>
        <h2>Confirm Logout</h2>
        <p>Are you sure you want to log out?</p>
        <div className="modal-button-group">
          <Button text="Yes, Log Out" onClick={confirmLogout} className="btn" />
          <Button text="Cancel" onClick={cancelLogout} className="btn" />
        </div>
      </Modal>
    </div>
  );
};

export default SystemAdminPage;
