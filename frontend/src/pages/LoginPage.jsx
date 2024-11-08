import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Form from "../components/Form";
import { Box, Typography } from "@mui/material";
import { useAuth } from "../contexts/AuthContext";
import AuthService from "../services/authService";
import { fireToast } from "../components/fireToast";

const LoginPage = () => {
  // State to store user input for email and password
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // Destructure login and isAuthenticated from AuthContext
  const { login, isAuthenticated } = useAuth();

  // Hook for programmatic navigation
  const navigate = useNavigate();

  // Destructure the loginStudent function from AuthService
  const { loginStudent } = AuthService;

  // Handle the login form submission
  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      // Call loginStudent service with email and password
      const data = await loginStudent({ email, password });
      
      // Extract JWT token and username from the response
      const token = data?.jwtToken;
      const username = data?.username;  
      
      // Perform login by setting token and username in AuthContext
      login(token, username);

      fireToast("Logged in successfully", "success");
      // Navigate to the exams page upon successful login
      navigate('/student/exams');
    } catch (error) {
      fireToast("Invalid credentials", "error");
    }
  };

  // Effect to redirect authenticated users away from login page
  useEffect(() => { 
    if (isAuthenticated) {
      navigate('/student/exams');  
    }
  }, [isAuthenticated, navigate]);

  // Define form fields to be used in the Form component
  const formFields = [
    { label: "Email", name: "email", type: "email", value: email, onChange: (e) => setEmail(e.target.value) },
    { label: "Password", name: "password", type: "password", value: password, onChange: (e) => setPassword(e.target.value) },
  ];

  return (
    <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      {/* Form component for login, passing form details and handlers */}
      <Form
        title="Login Student"
        buttonLabel="Login"
        onSubmit={handleLogin}
        fields={formFields}
      >
        {/* Forgot password link */}
        <Box sx={{ mt: 2, textAlign: "right" }}>
          <Link to="/forgot-password" style={{ textDecoration: "none" }}>
            <Typography variant="body2" color="primary">
              Forgot Password?
            </Typography>
          </Link>
        </Box>
      </Form>
    </Box>
  );
};

export default LoginPage;