import React, { useEffect, useState } from "react";
import Form from "../components/Form"; 
import { fireToast } from "../components/fireToast";
import { Box } from "@mui/material";
import { useLocation } from "react-router-dom";
import AuthService from "../services/authService";

const ResetPassword = () => {
  // State to store new password and confirm password inputs
  const [passwords, setPasswords] = useState({
    newPassword: "",
    confirmPassword: ""
  });

  // Location hook to access URL query parameters
  const location = useLocation();

  // State to store the reset token from the URL
  const [token, setToken] = useState(null);

  // Destructure resetPassword function from AuthService
  const { resetPassword } = AuthService;

  // Retrieve the reset token from URL parameters on component mount
  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const tokenParam = queryParams.get("token");
    console.log(tokenParam);
    setToken(tokenParam);
  }, [location]);

  // Handle form submission for password reset
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Check if new password and confirm password match
    if (passwords.newPassword !== passwords.confirmPassword) {
      fireToast("Passwords do not match", "error");
    } else {
      try {
        // Call resetPassword service with token and new password
        await resetPassword({ token: token, newPassword: passwords.newPassword });
        
        // Show success message and reset form fields
        fireToast("Password reset successfully", "success");
        setPasswords({
          newPassword: "",
          confirmPassword: ""
        });
      } catch (error) {
        // Show error message if password reset fails
        fireToast("An error occurred " + error.message, "error");
      }
    }
  };

  // Handle changes to form fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setPasswords((prevPasswords) => ({
      ...prevPasswords,
      [name]: value
    }));
  };

  // Define form fields for new password and confirm password inputs
  const fields = [
    {
      label: "New Password",
      type: "password",
      name: "newPassword",
      value: passwords.newPassword,
      onChange: handleChange
    },
    {
      label: "Confirm Password",
      type: "password",
      name: "confirmPassword",
      value: passwords.confirmPassword,
      onChange: handleChange
    }
  ];

  return (
    <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      {/* Render the form component for password reset */}
      <Form title="Reset Password" buttonLabel="Reset Password" fields={fields} onSubmit={handleSubmit} />
    </Box>
  );
};

export default ResetPassword;