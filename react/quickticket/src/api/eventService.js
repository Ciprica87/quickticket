import apiClient from './index';

export const createEvent = async (eventData) => {
  try {
    const userId = sessionStorage.getItem('userId');
    console.log('Retrieved user ID from session:', userId);

    if (!userId) {
      throw new Error('User ID not found. Please log in again.');
    }

    const eventDTO = { ...eventData, eventManagerId: userId };
    console.log('Creating event with data:', eventDTO);

    const response = await apiClient.post('/events/create', eventDTO);
    console.log('Event creation response:', response.data);

    return response.data.data;
  } catch (error) {
    console.error('Error creating event:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error creating event');
  }
};

export const createTicketTier = async (eventId, ticketTierData) => {
  try {
    console.log(`Creating ticket tier for event ID ${eventId} with data:`, ticketTierData);

    const response = await apiClient.post(`/ticket-tiers/create/${eventId}`, ticketTierData);
    console.log('Ticket tier creation response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error creating ticket tier:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error creating ticket tier');
  }
};

export const getAllEvents = async () => {
  try {
    console.log('Fetching all events');

    const response = await apiClient.get('/events/all');
    console.log('All events fetched:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error fetching events:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error fetching events');
  }
};

export const getEventsByManagerId = async (eventManagerId) => {
  try {
    console.log(`Fetching events for manager ID ${eventManagerId}`);

    const response = await apiClient.get(`/events/manager/${eventManagerId}`);
    console.log('Events fetched for manager:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error fetching events:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error fetching events');
  }
};

export const getTicketTiersByEventId = async (eventId) => {
  try {
    console.log(`Fetching ticket tiers for event ID ${eventId}`);

    const response = await apiClient.get(`/ticket-tiers/event/${eventId}`);
    console.log('Ticket tiers fetched:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error fetching ticket tiers:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error fetching ticket tiers');
  }
};

export const getTicketUsageByTier = async (ticketTierId) => {
  try {
    console.log(`Fetching ticket usage for tier ID ${ticketTierId}`);

    const response = await apiClient.get(`/ticket-tiers/${ticketTierId}/usage`);
    console.log('Ticket usage data fetched:', response.data.data);

    return response.data.data;
  } catch (error) {
    console.error('Error fetching ticket usage:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error fetching ticket usage');
  }
};

export const createStaffForEvent = async (eventId, staffData) => {
  try {
    console.log(`Creating staff for event ID ${eventId} with data:`, staffData);

    const response = await apiClient.post(`/staff/create/${eventId}`, staffData);
    console.log('Staff creation response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error creating staff:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error creating staff');
  }
};

export const deleteStaff = async (staffId) => {
  try {
    console.log(`Deleting staff with ID ${staffId}`);

    const response = await apiClient.delete(`/staff/delete/${staffId}`);
    console.log('Staff deletion response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error deleting staff:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error deleting staff');
  }
};

export const getStaffByEventId = async (eventId) => {
  try {
    console.log(`Fetching staff for event ID ${eventId}`);

    const response = await apiClient.get(`/staff/event/${eventId}`);
    console.log('Staff data fetched:', response.data.data);

    return response.data.data;
  } catch (error) {
    console.error('Error fetching staff:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error fetching staff');
  }
};

export const closeEvent = async (eventId) => {
  try {
    console.log(`Closing event ID ${eventId}`);

    const response = await apiClient.put(`/events/close/${eventId}`);
    console.log('Event close response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error closing event:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error closing event');
  }
};

export const fetchEvents = async () => {
  try {
    console.log('Fetching all events');

    const response = await apiClient.get('/events/all');
    console.log('All events fetched:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error fetching events:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error fetching events');
  }
};

export const updateEvent = async (eventId, eventData) => {
  try {
    console.log(`Updating event ID ${eventId} with data:`, eventData);

    const response = await apiClient.put(`/events/update/${eventId}`, eventData);
    console.log('Event update response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error updating event:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error updating event');
  }
};

export const deleteEvent = async (eventId) => {
  try {
    console.log(`Deleting event ID ${eventId}`);

    const response = await apiClient.delete(`/events/delete/${eventId}`);
    console.log('Event deletion response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error deleting event:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error deleting event');
  }
};

export const sendEventTicketsEmail = async (eventId) => {
  try {
    console.log(`Sending event tickets email for event ID ${eventId}`);

    const response = await apiClient.post(`/events/generate-and-send-tickets/${eventId}`);
    console.log('Event tickets email response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Error sending event tickets email:', error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || 'Error sending event tickets email');
  }
};
