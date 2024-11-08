import React, { useEffect, useState } from "react";
import Form from "../components/Form"; 
import { fireToast } from "../components/fireToast";
import { Box } from "@mui/material";
import { useLocation } from "react-router-dom";
import AuthService from "../services/authService";

// ResetPassword component allows users to set a new password after requesting a password reset
const ResetPassword = () => {
  // State to hold new and confirm password input values
  const [passwords, setPasswords] = useState({
    newPassword: "",
    confirmPassword: ""
  });

  // useLocation hook is used to get the current URL location
  const location = useLocation();

  // State to store the password reset token from URL parameters
  const [token, setToken] = useState(null);

  // Destructures the resetPassword method from AuthService
  const { resetPassword } = AuthService;

  // Extracts the token parameter from the URL when the component mounts or location changes
  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    
    const tokenParam = queryParams.get("token");
    console.log(tokenParam);
    
    setToken(tokenParam);
  }, [location]);

  // Handles form submission for password reset
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Checks if the new password and confirm password fields match
    if (passwords.newPassword !== passwords.confirmPassword) {
      fireToast("Passwords do not match", "error");
    } else {
      try {
        // Calls resetPassword method with token and new password to reset the password
        await resetPassword({ token: token, newPassword: passwords.newPassword });
        
        // Shows a success message and clears the password fields
        fireToast("Password reset successfully", "success");
        setPasswords({
          newPassword: "",
          confirmPassword: ""
        });
      } catch (error) {
        // Displays an error message if the password reset fails
        fireToast("An error occurred " + error.message, "error");
      }
    }
  };

  // Handles input change for password fields and updates state accordingly
  const handleChange = (e) => {
    const { name, value } = e.target;
    setPasswords((prevPasswords) => ({
      ...prevPasswords,
      [name]: value
    }));
  };

  // Array defining input fields for the Form component
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
    // Main container styling for centering and padding
    <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      
      {/* Form component renders a form with title, button, and password fields */}
      <Form title="Reset Password" buttonLabel="Reset Password" fields={fields} onSubmit={handleSubmit} />
    </Box>
  );
};

export default ResetPassword;