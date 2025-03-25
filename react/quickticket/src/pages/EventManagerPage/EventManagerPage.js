import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getEventsByManagerId, getTicketTiersByEventId } from '../../api/eventService';
import Button from '../../components/Button';
import EventCard from './EventCard';
import EventDetailsPanel from './EventDetailsPanel';
import CreateEventForm from './CreateEventForm';
import Modal from '../../components/Modal';
import './EventManagerPage.css';

const EventManagerPage = () => {
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [ticketTiers, setTicketTiers] = useState([]);
  const [isCreating, setIsCreating] = useState(false);
  const [isLogoutModalOpen, setIsLogoutModalOpen] = useState(false); 
  const navigate = useNavigate();

  const fetchEvents = async () => {
    try {
      const eventManagerId = sessionStorage.getItem('userId'); 
      const response = await getEventsByManagerId(eventManagerId);
      setEvents(response.data);
    } catch (error) {
      console.error('Error fetching events:', error.message);
    }
  };

  useEffect(() => {
    const token = sessionStorage.getItem('token');

    if (!token) {
      navigate('/login');
    } else {
      fetchEvents();
    }
  }, [navigate]);

  const handleCreateEventClick = () => {
    setIsCreating(!isCreating);
  };

  const handleEventClick = async (event) => {
    setSelectedEvent(event);

    try {
      const response = await getTicketTiersByEventId(event.id);
      setTicketTiers(response.data);
    } catch (error) {
      console.error('Error fetching ticket tiers:', error.message);
    }
  };

  const handleCloseEvent = async (eventId) => {
    await fetchEvents(); 
    setSelectedEvent(null); 
  };
  

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

  const handleEventCreated = () => {
    setIsCreating(false);
    fetchEvents();
  };

  return (
    <div className="event-manager-page">
      <div className="top-bar">
        <h2>{isCreating ? 'Create Event' : 'Your Events'}</h2>
        <div className="button-group">
          <Button className="toggle-button" text={isCreating ? 'Dashboard' : 'Create Event'} onClick={handleCreateEventClick} />
          <Button className="logout-button" text="Log Out" onClick={handleLogout} />
        </div>
      </div>
      <div className="content">
        {isCreating ? (
          <CreateEventForm onEventCreated={handleEventCreated} />
        ) : (
          <>
            <div className="event-list-container">
              {events.map((event) => (
                <EventCard key={event.id} event={event} onClick={() => handleEventClick(event)} />
              ))}
            </div>
            <div className="details-container">
              {selectedEvent ? (
                <EventDetailsPanel
                  event={selectedEvent}
                  ticketTiers={ticketTiers}
                  onCloseEvent={() => handleCloseEvent(selectedEvent.id)}
                />
              ) : (
                <p>Select an event to see details</p>
              )}
            </div>
          </>
        )}
      </div>

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

export default EventManagerPage;
