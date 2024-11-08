import React, { useState } from "react";
import Form from "../components/Form";
import { Box, Typography } from "@mui/material";
import AuthService from "../services/authService";
import { fireToast } from "../components/fireToast";
import { Link, useNavigate } from "react-router-dom";

const ForgotPassword = () => {
  // State to store the user's email input
  const [email, setEmail] = useState("");

  // Destructure forgotPassword function from AuthService
  const { forgotPassword } = AuthService;

  // Hook for programmatic navigation
  const navigate = useNavigate();

  // Function to handle form submission for password reset
  const handleForgotPassword = (e) => {
    e.preventDefault();

    try {
      // Call forgotPassword service to send the reset link to user's email
      forgotPassword({ email });
      fireToast("Password reset link sent:", "success");
      // Navigate back to the login page
      navigate("/");
    } catch (error) {
      fireToast("Invalid email", "error");
    }
  };

  return (
    <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      {/* Form component for entering email to receive password reset link */}
      <Form
        title="Forgot Password"
        buttonLabel="Send Reset Link"
        onSubmit={handleForgotPassword}
        fields={[
          {
            label: "Email",
            name: "email",
            type: "email",
            value: email,
            onChange: (e) => setEmail(e.target.value),
          },
        ]}
      >
        {/* Instruction text */}
        <Typography variant="body2" sx={{ mt: 2 }}>
          Enter your registered email address, and we’ll send you a link to reset your password.
        </Typography>

        {/* Link to navigate back to the login page */}
        <Box sx={{ mt: 2, textAlign: "right" }}>
          <Link to="/" style={{ textDecoration: "none" }}>
            <Typography variant="body2" color="primary">
              Go to login
            </Typography>
          </Link>
        </Box>
      </Form>
    </Box>
  );
};

export default ForgotPassword;