import React, { useEffect, useState } from 'react';
import authService from '../services/authService';
import { Container, Box, Typography } from '@mui/material';
import { useAuth } from '../contexts/AuthContext';
import { Link, useNavigate } from 'react-router-dom';
import { fireToast } from '../components/fireToast';
import Form from '../components/Form';

const LoginPage = () => {
  // State for storing email and password inputs
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  
  // Get login function and authentication status from AuthContext
  const { login, isAuthenticated } = useAuth();
  
  // useNavigate hook to programmatically navigate to other routes
  const navigate = useNavigate();

  // Function to handle form submission for login
  const handleLogin = async (event) => {
    event.preventDefault(); // Prevent page reload on form submission
    try {
      // Call the login function from authService with email and password
      const data = await authService.login({ email, password });
      const token = data?.jwtToken; // Extract JWT token from response
      
      // Use login function from AuthContext to store token in auth state
      login(token);
      
      // Redirect to the student registration page upon successful login
      navigate('/admin/register-student');
      
      // Display success toast message
      fireToast("Logged in successfully", "success");
    } catch (error) {
      console.log(error); // Log any error to the console
      
      // Display error toast message if login fails
      fireToast("Invalid credentials", "error");
    }
  };

  // Effect to automatically redirect if user is already authenticated
  useEffect(() => { 
    if (isAuthenticated) {
      navigate('/admin/register-student'); // Redirect to registration if authenticated
    }
  }, [isAuthenticated, navigate]);

  // Configuration for form fields passed to Form component
  const formFields = [
    { 
      label: "Email", 
      name: "email", 
      type: "email", 
      value: email, 
      onChange: (e) => setEmail(e.target.value) // Update email state on change
    },
    { 
      label: "Password", 
      name: "password", 
      type: "password", 
      value: password, 
      onChange: (e) => setPassword(e.target.value) // Update password state on change
    },
  ];

  return (
    // Centered container for login form
    <Container maxWidth="xs">
      <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* Form component with title, fields, and submit handler */}
        <Form 
          title="Admin Login" 
          buttonLabel="Sign In" 
          fields={formFields} 
          onSubmit={handleLogin} 
        >
          {/* Additional content or links can be added here */}
          <Box sx={{ mt: 2, textAlign: "right" }}>
            <Link to="/forgot-password" style={{ textDecoration: "none" }}>
              <Typography variant="body2" color="primary">
                Forgot Password?
              </Typography>
            </Link>
          </Box>
        </Form>
      </Box>
    </Container>
  );
};

export default LoginPage;