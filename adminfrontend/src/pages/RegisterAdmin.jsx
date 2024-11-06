import React, { useState } from "react"; 
import { fireToast } from "../components/fireToast"; 
import Form from "../components/Form"; 
import authService from "../services/authService";
import AuthHeader from "../Helper/AuthHeader";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const RegisterAdmin = () => {
  // Destructure the registerAdmin method from authService
  const { registerAdmin } = authService;
  const headers = AuthHeader(); // Get authorization headers to pass with requests
  const navigate = useNavigate(); // Hook for navigation to redirect the user after actions
  const { logout } = useAuth(); // Destructure logout function from AuthContext

  // useState to manage form values for username, email, and password
  const [formValues, setFormValues] = useState({
    username: "",
    email: "",
    password: "",
  });

  // Handle form submission logic
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent default form submission behavior
    try {
      // Call the registerAdmin API to register the admin using form data and headers
      await registerAdmin(formValues, headers);
      fireToast("Admin registered successfully", "success"); // Display success message upon successful registration
    } catch (error) {
      // Handle specific error responses for unauthorized access (401) or forbidden access (403)
      if (error.response.status === 401 || error.response.status === 403) {
        logout(); // Logout user if unauthorized/forbidden error occurs
        navigate("/");  // Redirect user to the login page
      }
      fireToast("Error registering admin " + error.message, "error"); // Display error message if the registration fails
    }
  };

  // Handle changes in the form input fields
  const handleChange = (e) => {
    const { name, value } = e.target; // Get the name and value of the changed input field
    setFormValues((prevValues) => ({
      ...prevValues, // Spread previous values
      [name]: value, // Update the specific form field with the new value
    }));
  };

  // Define the form fields (username, email, and password)
  const formFields = [
    { label: "Username", name: "username", value: formValues.username, onChange: handleChange },
    { label: "Email", name: "email", type: "email", value: formValues.email, onChange: handleChange },
    { label: "Password", name: "password", type: "password", value: formValues.password, onChange: handleChange },
  ];

  // Render the Form component with necessary props
  return (
    <Form
      title="Register Admin" // Title for the form
      buttonLabel="Register Admin" // Button label for submission
      fields={formFields} // Pass the form fields to Form
      onSubmit={handleSubmit} // Handle form submission
    />
  );
};

export default RegisterAdmin; 