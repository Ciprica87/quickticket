import React, { useState } from 'react';
import { createEvent, createTicketTier, sendEventTicketsEmail } from '../../api/eventService';
import Button from '../../components/Button';
import './CreateEventForm.css';

const CreateEventForm = ({ onEventCreated }) => {
  const [name, setName] = useState('');
  const [location, setLocation] = useState('');
  const [date, setDate] = useState('');
  const [ticketTiers, setTicketTiers] = useState([]);
  const [tierName, setTierName] = useState('');
  const [tierDescription, setTierDescription] = useState('');
  const [tierTotal, setTierTotal] = useState('');
  const [error, setError] = useState(null);

  const handleAddTier = () => {
    if (tierName && tierDescription && tierTotal) {
      setTicketTiers([...ticketTiers, { name: tierName, description: tierDescription, total: tierTotal }]);
      setTierName('');
      setTierDescription('');
      setTierTotal('');
    } else {
      console.warn('Tier fields are not fully filled');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Submitting event:', { name, location, date, ticketTiers });
  
    try {
      const eventData = { name, location, date }; 
      const eventId = await createEvent(eventData);  
  
      if (eventId && ticketTiers.length > 0) {
        console.log('Event ID:', eventId);
  
        for (const tier of ticketTiers) {
          const ticketTierData = {
            name: tier.name,
            description: tier.description,
            amount: tier.total,
          };
          console.log(`Sending request to create ticket tier:`, ticketTierData);
          await createTicketTier(eventId, ticketTierData);
          console.log(`Request sent for ticket tier: ${tier.name}`);
        }
      } else if (ticketTiers.length === 0) {
        console.warn('No ticket tiers to create');
      }

      console.log('Sending event tickets email...');
      await sendEventTicketsEmail(eventId);

      onEventCreated();
      console.log('Event, ticket tiers, and email sent successfully');
    } catch (error) {
      console.error('Error creating event or ticket tiers:', error.message);
      setError(error.message);
    }
  };
  
  

  return (
    <form className="create-event-form" onSubmit={handleSubmit}>
      <div className="left-section">
        <h3>Details</h3>
        <div className="form-group">
          <label>Event Name</label>
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
        </div>
        <div className="form-group">
          <label>Location</label>
          <input type="text" value={location} onChange={(e) => setLocation(e.target.value)} required />
        </div>
        <div className="form-group">
          <label>Date</label>
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} required />
        </div>
      </div>
      <div className="right-section">
        <h3>Ticket Tiers</h3>
        <div className="ticket-tier-form">
          <div className="form-group">
            <label>Tier Name</label>
            <input type="text" value={tierName} onChange={(e) => setTierName(e.target.value)} />
          </div>
          <div className="form-group">
            <label>Description</label>
            <input type="text" value={tierDescription} onChange={(e) => setTierDescription(e.target.value)} />
          </div>
          <div className="form-group">
            <label>Total Tickets</label>
            <input type="number" value={tierTotal} onChange={(e) => setTierTotal(e.target.value)} />
          </div>
          <Button text="Add Tier" type="button" className="add-tier-btn" onClick={handleAddTier} />
        </div>
        <div className="ticket-tiers-list">
          {ticketTiers.map((tier, index) => (
            <div key={index} className="ticket-tier-card">
              <div className="ticket-tier-header">
                <h4 className="ticket-tier-name">{tier.name}</h4>
                <p className="ticket-tier-total">Total: {tier.total}</p>
                <Button text="Delete" type="button" className="small-btn" />
              </div>
              <p className="ticket-tier-description">{tier.description}</p>
            </div>
          ))}
        </div>
      </div>
      <div className="finalize-button">
        <Button text="Finalize Event" type="submit" />
      </div>
      {error && <div className="error-message">{error}</div>}
    </form>
  );
};

export default CreateEventForm;
