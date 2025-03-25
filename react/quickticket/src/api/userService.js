import apiClient from './index';

export const fetchUsers = async () => {
  try {
    const response = await apiClient.get('/users/all');
    return response.data.data; 
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Error fetching users');
  }
};

export const updateUser = async (userId, updatedData) => {
  try {
    const response = await apiClient.put(`/users/update/${userId}`, updatedData);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Error updating user');
  }
};

export const deleteUser = async (userId) => {
  try {
    const response = await apiClient.delete(`/users/delete/${userId}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Error deleting user');
  }
};
