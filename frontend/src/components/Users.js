import * as React from 'react';
import { useState, useEffect } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import Popover from '@mui/material/Popover';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const paperStyle = {
  padding: '20px',
  width: '400px',
  margin: '20px auto',
};

const listStyle = {
  listStyleType: 'none',
  padding: 0,
};

const addUserLabelStyle = {
  fontSize: '1.2rem',
  fontWeight: 'bold',
};

const cursorPointer = {
  cursor: 'pointer',
};

const userItemStyle = {
  border: '1px solid #ccc',
  borderRadius: '5px',
  padding: '8px',
};

export default function User() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
  });

  const [users, setUsers] = useState([]);
  const [anchorEl, setAnchorEl] = useState(null);
  const [selectedUserId, setSelectedUserId] = useState(null);
  const [selectedUserName, setSelectedUserName] = useState(null);
  const [newName, setNewName] = useState('');
  const [newEmail, setNewEmail] = useState('');

  const handleChange = (event) => {
    const { id, value } = event.target;
    setFormData({ ...formData, [id]: value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    axios.post('http://localhost:8080/users/create', formData)
      .then(response => {
        console.log('Success:', response);
        toast.success(`${formData.name} added successfully!`);
        setFormData({ name: '', email: '' });
        handleAnotherSubmit();
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  const handleDelete = (id, name) => {
    axios.delete(`http://localhost:8080/users/delete/${id}`)
      .then(response => {
        console.log('Success:', response);
        toast.success(`${name} deleted successfully!`);
        handleAnotherSubmit();
        handleClose();
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  const handleUpdate = () => {
    const newData = {
      name: newName,
      email: newEmail,
    };

    axios.put(`http://localhost:8080/users/update/${selectedUserId}`, newData)
      .then(response => {
        console.log('Success:', response);
        toast.success(`${newName} updated successfully!`);
        handleAnotherSubmit();
        handleClose();
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  const handleAnotherSubmit = () => {
    axios.get('http://localhost:8080/users')
      .then(response => {
        console.log('Success:', response);
        setUsers(response.data);
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  useEffect(() => {
    handleAnotherSubmit();
  }, []);

  const handleClick = (event, id, name, email) => {
    setAnchorEl(event.currentTarget);
    setSelectedUserId(id);
    setSelectedUserName(name);
    setNewName(name);
    setNewEmail(email);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setSelectedUserId(null);
    setSelectedUserName(null);
    setNewName('');
    setNewEmail('');
  };

  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <div>
          <h2>All Users</h2>
          <ul style={listStyle}>
            {users.map((user, index) => (
              <React.Fragment key={user.id}>
                <li style={{ ...cursorPointer, ...userItemStyle }} onClick={(event) => handleClick(event, user.id, user.name, user.email)}>{user.name} - {user.email}</li>
                {index !== users.length - 1 && <hr />}
              </React.Fragment>
            ))}
          </ul>
          <Popover
            id={id}
            open={open}
            anchorEl={anchorEl}
            onClose={handleClose}
            anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'center',
            }}
            transformOrigin={{
                vertical: 'top',
                horizontal: 'center',
            }}
            >
            <Box sx={{ p: 2 }}>
                <TextField id="newName" label="New Name" variant="outlined" fullWidth value={newName} onChange={(e) => setNewName(e.target.value)} />
                <Box mt={1}>
                <TextField id="newEmail" label="New Email" variant="outlined" fullWidth value={newEmail} onChange={(e) => setNewEmail(e.target.value)} />
                </Box>
                <Box mt={2}>
                <Button onClick={handleUpdate} variant="contained" color="primary" fullWidth>Update</Button>
                </Box>
                <Box mt={1}> {/* Добавляем расстояние между кнопками */}
                <Button onClick={() => handleDelete(selectedUserId, selectedUserName)} variant="contained" color="error" fullWidth>Delete</Button>
                </Box>
            </Box>
            </Popover>
        </div>
      </Paper>

      <Paper elevation={3} style={paperStyle}>
        <form onSubmit={handleSubmit}>
          <Box
            sx={{
              '& > :not(style)': { m: 1, width: '100%' },
            }}
            noValidate
            autoComplete="off"
          >
            <Box mb={2}>
              <label htmlFor="username" style={addUserLabelStyle}>Add User</label>
            </Box>
            <TextField id="name" label="Username" variant="outlined" fullWidth value={formData.name} onChange={handleChange} />
            <TextField id="email" label="Email" variant="outlined" fullWidth value={formData.email} onChange={handleChange} />
            <Button type="submit" variant="contained" color="primary" fullWidth>
              Add User
            </Button>
          </Box>
        </form>
      </Paper>
    </Container>
  );
}
