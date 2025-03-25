import apiClient from './index';
import { setToken, removeToken, setRole } from './tokenService';

export const login = async (username, password) => {
  try {
    console.log('Attempting to log in with:', { username, password });

    const response = await apiClient.post('/auth/login', { username, password });

    console.log('Login response:', response.data);

    if (response.data.success) {
      const { token, role, userId } = response.data.data;

      console.log('Login successful, received data:', { token, role, userId });

      setToken(token);
      setRole(role);

      if (userId) {
        sessionStorage.setItem('userId', userId);
        console.log('User ID saved to session storage:', userId);
      }
    } else {
      console.warn('Login failed:', response.data.message);
    }

    return response.data;
  } catch (error) {
    console.error('Login error:', error.response?.data?.message || 'Login failed');
    throw new Error(error.response?.data?.message || 'Login failed');
  }
};

export const signup = async (userData) => {
  try {
    console.log('Attempting to sign up with data:', userData);

    const response = await apiClient.post('/auth/signup', userData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    console.log('Signup response:', response.data);

    return response.data;
  } catch (error) {
    console.error('Signup error:', error.response?.data?.message || 'Signup failed');
    throw new Error(error.response?.data?.message || 'Signup failed');
  }
};

export const logout = () => {
  console.log('Logging out...');
  
  removeToken();
  sessionStorage.removeItem('userId'); 

  console.log('Token removed and user ID cleared from session storage.');
};
