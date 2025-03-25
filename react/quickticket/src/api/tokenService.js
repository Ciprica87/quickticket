export const getToken = () => sessionStorage.getItem('token');

export const setToken = (token) => {
  sessionStorage.setItem('token', token);
};

export const removeToken = () => {
  sessionStorage.removeItem('token');
  sessionStorage.removeItem('role');
};

export const getRole = () => sessionStorage.getItem('role');

export const setRole = (role) => {
  sessionStorage.setItem('role', role);
};
