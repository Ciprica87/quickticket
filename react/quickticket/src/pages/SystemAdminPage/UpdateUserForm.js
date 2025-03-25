import React, { useState } from 'react';
import Button from '../../components/Button';
import './UpdateUserForm.css';

const UpdateUserForm = ({ user, onUpdate, onClose }) => {
  const [username, setUsername] = useState(user.username);
  const [email, setEmail] = useState(user.email);
  const [role, setRole] = useState(user.role);

  const handleSubmit = (e) => {
    e.preventDefault();
    onUpdate({ ...user, username, email, role });
  };

  return (
    <form className="update-user-form" onSubmit={handleSubmit}>
      <h2>Update User</h2>
      <div className="form-group">
        <label>Username</label>
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
      </div>
      <div className="form-group">
        <label>Email</label>
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
      </div>
      <div className="form-group">
        <label>Role</label>
        <select value={role} onChange={(e) => setRole(e.target.value)} required>
          <option value="staff">Staff</option>
          <option value="eventmanager">Event Manager</option>
          <option value="systemadmin">System Admin</option>
        </select>
      </div>
      <Button text="Update" type="submit" className="btn" />
    </form>
  );
};

export default UpdateUserForm;
