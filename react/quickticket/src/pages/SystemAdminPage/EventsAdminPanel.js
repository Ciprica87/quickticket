import React, { useState, useEffect } from 'react';
import Button from '../../components/Button';
import Modal from '../../components/Modal';
import UpdateEventForm from './UpdateEventForm';
import './EventsAdminPanel.css';
import { fetchEvents, updateEvent, deleteEvent } from '../../api/eventService'; 

const EventsAdminPanel = () => {
  const [events, setEvents] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const getEvents = async () => {
      try {
        const response = await fetchEvents();
        setEvents(response.data); 
      } catch (error) {
        console.error('Error fetching events:', error);
      }
    };
    getEvents();
  }, []);

  const handleSearch = () => {
    setEvents(events.filter(event => event.name.toLowerCase().includes(searchTerm.toLowerCase())));
  };

  const handleSelectEvent = (event) => {
    setSelectedEvent(event);
  };

  const handleUpdateEvent = () => {
    if (selectedEvent) {
      setIsModalOpen(true);
    }
  };

  const handleUpdate = async (updatedEvent) => {
    try {
      await updateEvent(updatedEvent.id, updatedEvent); 
      setEvents(events.map(event => (event.id === updatedEvent.id ? updatedEvent : event)));
      setIsModalOpen(false);
    } catch (error) {
      console.error('Error updating event:', error);
    }
  };

  const handleDeleteEvent = async () => {
    if (selectedEvent) {
      try {
        await deleteEvent(selectedEvent.id); 
        setEvents(events.filter(event => event.id !== selectedEvent.id));
        setSelectedEvent(null);
      } catch (error) {
        console.error('Error deleting event:', error);
      }
    }
  };

  return (
    <div className="events-admin-panel">
      <div className="events-admin-header">
        <h3>Manage Events</h3>
        <div className="search-bar">
          <input
            type="text"
            placeholder="Search event name"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <Button text="Search" onClick={handleSearch} />
        </div>
      </div>
      <div className="events-list-container">
        {events.map((event) => (
          <div
            key={event.id}
            className={`event-card ${selectedEvent?.id === event.id ? 'selected' : ''}`}
            onClick={() => handleSelectEvent(event)}
          >
            <div className="event-details">
              <h4>{event.name}</h4>
              <p><span>Location:</span> {event.location}</p>
              <p className="event-date"><span>Date:</span> {event.date}</p>
            </div>
          </div>
        ))}
      </div>
      <div className="events-actions">
        <Button text="Update" onClick={handleUpdateEvent} disabled={!selectedEvent} />
        <Button text="Delete" onClick={handleDeleteEvent} disabled={!selectedEvent} className="delete-btn" />
      </div>
      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
        {selectedEvent && (
          <UpdateEventForm event={selectedEvent} onUpdate={handleUpdate} onClose={() => setIsModalOpen(false)} />
        )}
      </Modal>
    </div>
  );
};

export default EventsAdminPanel;
