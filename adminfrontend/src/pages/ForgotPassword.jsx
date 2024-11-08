import React, { useState } from "react";
import Form from "../components/Form";
import { Box, Typography } from "@mui/material";
import AuthService from "../services/authService";
import { fireToast } from "../components/fireToast";
import { Link, useNavigate } from "react-router-dom";

// ForgotPassword component allows users to request a password reset link via email
const ForgotPassword = () => {
  // State to store the email input value
  const [email, setEmail] = useState("");

  // Destructures the forgotPassword method from AuthService
  const { forgotPassword } = AuthService;

  // Initialize navigate to redirect users after submitting the form
  const navigate = useNavigate();

  // Handles form submission for the password reset request
  const handleForgotPassword = (e) => {
    e.preventDefault();

    try {
      // Calls forgotPassword method to send the reset link to the provided email
      forgotPassword({ email });
      
      // Shows a success message and navigates the user to the login page
      fireToast("Password reset link sent: " + email, "success");
      navigate("/");
    } catch (error) {
      // Shows an error message if the email is invalid or an error occurs
      fireToast("Invalid email", "error");
    }
  };

  return (
    // Main container styling for centering and padding
    <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>

      {/* Form component renders a form with title, button, and email input */}
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
        {/* Additional informational text for the user */}
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