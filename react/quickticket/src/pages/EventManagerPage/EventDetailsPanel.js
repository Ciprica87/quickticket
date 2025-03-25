import React, { useState, useEffect } from 'react';
import Button from '../../components/Button';
import Modal from '../../components/Modal';
import { closeEvent, getTicketUsageByTier, getStaffByEventId, createStaffForEvent, deleteStaff } from '../../api/eventService'; 
import './EventDetailsPanel.css';

const EventDetailsPanel = ({ event, ticketTiers, onCloseEvent }) => {
  const [view, setView] = useState('ticketTiers');
  const [staffList, setStaffList] = useState([]);
  const [selectedStaff, setSelectedStaff] = useState(null);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isConfirmCloseModalOpen, setIsConfirmCloseModalOpen] = useState(false);
  const [newStaff, setNewStaff] = useState({ username: '', password: '', email: '' });
  const [ticketUsage, setTicketUsage] = useState({});

  useEffect(() => {
    if (event && event.id) {
      const fetchStaffList = async () => {
        try {
          const staffData = await getStaffByEventId(event.id);
          setStaffList(staffData);
        } catch (error) {
          console.error('Error fetching staff:', error.message);
        }
      };

      fetchStaffList();
    }
  }, [event]);

  useEffect(() => {
    const fetchTicketUsage = async () => {
      const usageData = {};
      for (const tier of ticketTiers) {
        const usage = await getTicketUsageByTier(tier.id);
        usageData[tier.id] = usage;
      }
      setTicketUsage(usageData);
    };

    if (ticketTiers.length > 0) {
      fetchTicketUsage();
    }
  }, [ticketTiers]);

  const handleSelectStaff = (staff) => {
    setSelectedStaff(staff);
  };

  const handleDeleteStaff = async () => {
    if (selectedStaff) {
      try {
        await deleteStaff(selectedStaff.id);
        setStaffList(staffList.filter((staff) => staff.id !== selectedStaff.id));
        setSelectedStaff(null);
      } catch (error) {
        console.error('Error deleting staff:', error.message);
      }
    }
  };

  const handleCreateStaff = async () => {
    try {
      await createStaffForEvent(event.id, newStaff);
      const updatedStaffList = await getStaffByEventId(event.id);
      setStaffList(updatedStaffList);
      setNewStaff({ username: '', password: '', email: '' });
      setIsCreateModalOpen(false);
    } catch (error) {
      console.error('Error creating staff:', error.message);
    }
  };

  const handleConfirmCloseEvent = async () => {
    try {
      await closeEvent(event.id);
      onCloseEvent(event.id);  
      setIsConfirmCloseModalOpen(false);
    } catch (error) {
      console.error('Error closing event:', error.message);
      setIsConfirmCloseModalOpen(false);
    }
  };

  const renderContent = () => {
    if (view === 'ticketTiers') {
      return (
        <div className="ticket-tiers-container">
          {ticketTiers.map((tier) => {
            const usage = ticketUsage[tier.id] || { usedTickets: 0, totalTickets: 0 };
            return (
              <div key={tier.id} className="ticket-tier-card">
                <div className="ticket-tier-header">
                  <h4 className="ticket-tier-name">{tier.name}</h4>
                  <p className="ticket-tier-used">Used: {usage.usedTickets}</p>
                  <p className="ticket-tier-total">Total: {usage.totalTickets}</p>
                </div>
                <p className="ticket-tier-description">{tier.description}</p>
              </div>
            );
          })}
        </div>
      );
    } else if (view === 'staff') {
      return (
        <div className="staff-container-wrapper">
          <div className="staff-container">
            {staffList.map((staff) => (
              <div
                key={staff.id}
                className={`staff-card ${selectedStaff?.id === staff.id ? 'selected' : ''}`}
                onClick={() => handleSelectStaff(staff)}
              >
                <div className="staff-info">
                  <p className="staff-username">Username: {staff.username}</p>
                  <p className="staff-email">Email: {staff.email}</p>
                </div>
              </div>
            ))}
          </div>
          <div className="staff-buttons">
            <button className="btn" onClick={() => setIsCreateModalOpen(true)}>Create</button>
            <button className="btn" onClick={handleDeleteStaff} disabled={!selectedStaff}>Delete</button>
          </div>
        </div>
      );
    }
  }; 

  return (
    <div className="event-details-panel">
      <div className="event-details-header">
        <h3 className="event-details-name">{event.name}</h3>
        <p className="event-details-date">Date: {event.date}</p>
        <p className="event-details-location">Location: {event.location}</p>
      </div>
      <div className="event-details-buttons-bar">
        <button
          className="btn close-event-btn"
          onClick={() => setIsConfirmCloseModalOpen(true)}
          disabled={event.name.endsWith("- Closed")}
        >
          Close Event
        </button>
        <h4 className="tab-title">{view === 'ticketTiers' ? 'Tickets per Tier' : 'Staff Members'}</h4>
        <button className="btn switch-view" onClick={() => setView(view === 'ticketTiers' ? 'staff' : 'ticketTiers')}>
          {view === 'ticketTiers' ? 'Switch to Staff' : 'Switch to Tickets'}
        </button>
      </div>
      {renderContent()}

      <Modal isOpen={isCreateModalOpen} onClose={() => setIsCreateModalOpen(false)}>
        <form className="create-staff-form" onSubmit={(e) => { e.preventDefault(); handleCreateStaff(); }}>
          <h2>Create Staff</h2>
          <div className="staff-form-group">
            <label>Username</label>
            <input
              type="text"
              value={newStaff.username}
              onChange={(e) => setNewStaff({ ...newStaff, username: e.target.value })}
              required
            />
          </div>
          <div className="staff-form-group">
            <label>Password</label>
            <input
              type="password"
              value={newStaff.password}
              onChange={(e) => setNewStaff({ ...newStaff, password: e.target.value })}
              required
            />
          </div>
          <div className="staff-form-group">
            <label>Email</label>
            <input
              type="email"
              value={newStaff.email}
              onChange={(e) => setNewStaff({ ...newStaff, email: e.target.value })}
              required
            />
          </div>
          <Button text="Create" type="submit" className="btn" />
        </form>
      </Modal>

      <Modal isOpen={isConfirmCloseModalOpen} onClose={() => setIsConfirmCloseModalOpen(false)}>
        <div className="confirm-close-event">
          <h2>Confirm Close Event</h2>
          <p>Are you sure you want to close this event? This action cannot be undone.</p>
          <div className="confirm-close-event-buttons">
            <button className="btn" onClick={handleConfirmCloseEvent}>Yes, Close</button>
            <button className="btn" onClick={() => setIsConfirmCloseModalOpen(false)}>Cancel</button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default EventDetailsPanel;
