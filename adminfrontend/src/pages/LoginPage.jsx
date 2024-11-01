import React from 'react';
import authService from '../services/authService';
import  { useState } from 'react';
import { Container, TextField, Button, Box, Typography } from '@mui/material';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  // State variables for email and password inputs
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { login, isAuthenticated } = useAuth(); // Destructuring login function and authentication status from context
  const navigate = useNavigate(); // Hook to programmatically navigate between routes
  
  // Function to handle login when the form is submitted
  const handleLogin = async (event) => {
    event.preventDefault(); // Prevent the default form submission behavior
    const data = await authService.login({ email, password }); // Attempt to login with provided email and password
    const token = data?.jwtToken; // Extract the JWT token from the response
    login(token); // Call the login function from the context to save the token

    navigate('/admin/register-user'); // Redirect to the register user page after successful login
  };

  // Redirect if the user is already authenticated
  if (isAuthenticated) {
    navigate('/admin/register-user'); // If authenticated, navigate to the register user page
  }

  return (
    <Container maxWidth="xs"> {/* Container for the login form with a maximum width */}
      <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}> {/* Flexbox layout for centering */}
        <Typography component="h1" variant="h5">Admin Login</Typography> {/* Title of the login page */}
        
        <Box component="form" onSubmit={handleLogin} sx={{ mt: 1 }}> {/* Form for login submission */}
          <TextField
            variant="outlined" 
            margin="normal" 
            required 
            fullWidth 
            label="Email" 
            type='email'
            autoFocus 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
          />
          <TextField
            variant="outlined" 
            margin="normal" 
            required 
            fullWidth 
            label="Password" 
            type="password" 
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Button
            type="submit" 
            fullWidth 
            variant="contained" 
            color="primary" 
            sx={{ mt: 3, mb: 2, py: 1.5 }} 
          >
            Sign In
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default LoginPage;
