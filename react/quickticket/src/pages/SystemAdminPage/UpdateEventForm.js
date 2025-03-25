import React, { useState } from 'react';
import Button from '../../components/Button';
import './UpdateEventForm.css';

const UpdateEventForm = ({ event, onUpdate, onClose }) => {
  const [name, setName] = useState(event.name);
  const [location, setLocation] = useState(event.location);
  const [date, setDate] = useState(event.date);

  const handleSubmit = (e) => {
    e.preventDefault();
    onUpdate({ ...event, name, location, date });
  };

  return (
    <form className="update-event-form" onSubmit={handleSubmit}>
      <h2>Update Event</h2>
      <div className="login-form-group">
        <label>Event Name</label>
        <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
      </div>
      <div className="login-form-group">
        <label>Location</label>
        <input type="text" value={location} onChange={(e) => setLocation(e.target.value)} required />
      </div>
      <div className="login-form-group">
        <label>Date</label>
        <input type="date" value={date} onChange={(e) => setDate(e.target.value)} required />
      </div>
      <Button text="Update" type="submit" className="btn" />
    </form>
  );
};

export default UpdateEventForm;
