import React, { useState, useEffect } from 'react';
import Button from '../../components/Button';
import Modal from '../../components/Modal';
import UpdateUserForm from './UpdateUserForm';
import { fetchUsers, updateUser, deleteUser } from '../../api/userService';
import './UsersAdminPanel.css';

const UsersAdminPanel = () => {
  const [users, setUsers] = useState([]);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedUser, setSelectedUser] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [filterRole, setFilterRole] = useState(''); 

  useEffect(() => {
    const getUsers = async () => {
      try {
        const usersData = await fetchUsers();
        setUsers(usersData);
        setFilteredUsers(usersData); 
      } catch (error) {
        console.error('Error fetching users:', error.message);
      }
    };

    getUsers();
  }, []);

  useEffect(() => {
    const filterUsers = () => {
      const filtered = users.filter(user => {
        const matchesSearchTerm = user.username.toLowerCase().includes(searchTerm.toLowerCase());
        const matchesRole = filterRole ? user.role.toLowerCase() === filterRole.toLowerCase() : true;
        return matchesSearchTerm && matchesRole;
      });

      setFilteredUsers(filtered);
    };

    filterUsers();
  }, [searchTerm, filterRole, users]);

  const handleSearch = () => {
    const filtered = users.filter(user =>
      user.username.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredUsers(filtered);
  };

  const handleSelectUser = (user) => {
    setSelectedUser(user);
  };

  const handleUpdateUser = () => {
    if (selectedUser) {
      setIsModalOpen(true);
    }
  };

  const handleUpdate = async (updatedUser) => {
    try {
      await updateUser(updatedUser.id, updatedUser);
      setUsers(users.map(user => (user.id === updatedUser.id ? updatedUser : user)));
      setIsModalOpen(false);
    } catch (error) {
      console.error('Error updating user:', error.message);
    }
  };

  const handleDeleteUser = async () => {
    if (selectedUser) {
      try {
        await deleteUser(selectedUser.id);
        setUsers(users.filter(user => user.id !== selectedUser.id));
        setSelectedUser(null);
      } catch (error) {
        console.error('Error deleting user:', error.message);
      }
    }
  };

  return (
    <div className="users-admin-panel">
      <div className="users-admin-header">
        <h3>Manage Users</h3>
        <div className="search-bar">
          <select
            value={filterRole}
            onChange={(e) => setFilterRole(e.target.value)}
            className="filter-dropdown"
          >
            <option value="">All Roles</option>
            <option value="staff">Staff</option>
            <option value="eventmanager">Event Manager</option>
            <option value="systemadmin">System Admin</option>
          </select>
          <input
            type="text"
            placeholder="Search username"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <Button text="Search" onClick={handleSearch} />
        </div>
      </div>
      <div className="users-list-container">
        {filteredUsers.map((user) => (
          <div
            key={user.id}
            className={`user-card ${selectedUser?.id === user.id ? 'selected' : ''}`}
            onClick={() => handleSelectUser(user)}
          >
            <div className="user-info">
              <h4 className="username">{user.username}</h4>
              <p className="email"><span>Email:</span> {user.email}</p>
              <p className="role"><span>Role:</span> {user.role}</p>
            </div>
          </div>
        ))}
      </div>
      <div className="users-actions">
        <Button text="Update" onClick={handleUpdateUser} disabled={!selectedUser} />
        <Button text="Delete" onClick={handleDeleteUser} disabled={!selectedUser} className="delete-btn" />
      </div>
      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
        {selectedUser && (
          <UpdateUserForm user={selectedUser} onUpdate={handleUpdate} onClose={() => setIsModalOpen(false)} />
        )}
      </Modal>
    </div>
  );
};

export default UsersAdminPanel;
