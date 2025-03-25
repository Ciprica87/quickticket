import React from 'react';
import './EventCard.css';

const EventCard = ({ event, onClick }) => {
  return (
    <div className="event-card" onClick={onClick}>
      <h3>{event.name}</h3>
      <p>Date: {event.date}</p>
      <p>Location: {event.location}</p>
    </div>
  );
};

export default EventCard;
