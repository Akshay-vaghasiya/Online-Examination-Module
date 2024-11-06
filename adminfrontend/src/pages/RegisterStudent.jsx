import React, { useState, useEffect } from "react";
import AuthHeader from "../Helper/AuthHeader";
import { fireToast } from "../components/fireToast";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import AddUniversityDialog from "../components/AddUniversityDialog";
import UniversityAutocomplete from "../components/UniversityAutocomplete";
import Form from "../components/Form";
import universityService from "../services/universityService";
import authService from "../services/authService";

const RegisterStudent = () => {
  const [universities, setUniversities] = useState([]);  // State to store universities list
  const [selectedUniversity, setSelectedUniversity] = useState(null);  // State for selected university
  const [openDialog, setOpenDialog] = useState(false);  // Dialog state for adding a new university
  const [newUniversity, setNewUniversity] = useState({  // State for new university data
    universityName: "",
    address: "",
    contactEmail: "",
    contactPhone: "",
    websiteUrl: "",
  });
  
  const headers = AuthHeader();  // Authorization headers for API requests
  const navigate = useNavigate();  // Navigation hook for routing
  const { logout } = useAuth();  // Logout function from AuthContext
  const { getAllUniversities, registerUniversity } = universityService;  // University service methods
  const { registerStudent } = authService;  // Auth service method to register a student

  // Fetch list of universities on component mount
  useEffect(() => {
    fetchUniversities();
  }, []);

  // Function to fetch universities
  const fetchUniversities = async () => {
    try {
      const response = await getAllUniversities(headers);  // Call to get all universities
      const universityNames = response?.map((university) => university.universityName);  // Extract names
      setUniversities(universityNames);  // Update universities state
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {  // Handle unauthorized and resource access error
        logout();
        navigate("/");  
      }
      fireToast("Error fetching universities " + error.message, "error");  // Show error message
    }
  };

  // Handle university selection or creation
  const handleUniversityChange = (event, value) => {
    if (value.substring(0, 5) === "Add: ") {  // If user chooses to add a new university
      setOpenDialog(true);  // Open the dialog to add university
    } else {
      setSelectedUniversity(value);  // Set the selected university
    }
  };

  // Add new university
  const handleAddUniversity = async () => {
    if (newUniversity.universityName === "") {  // Ensure university name is provided
      fireToast("Please enter university name", "error");
      return;
    }
    try {
      const addedUniversity = await registerUniversity(newUniversity, headers);  // Register the new university
      setUniversities([...universities, addedUniversity?.universityName]);  // Add new university to the list
      setSelectedUniversity(addedUniversity?.universityName);  // Set as selected
      setOpenDialog(false);  // Close dialog
      setNewUniversity({  // Reset new university fields
        universityName: "",
        address: "",
        contactEmail: "",
        contactPhone: "",
        websiteUrl: "",
      });
      fireToast("University added successfully", "success");  // Show success message
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {  // Handle unauthorized and resource access error
        logout();
        navigate("/");  
      }
      fireToast("Error adding university " + error.message, "error");  // Show error message
    }
  };

  // Handle form submission for registering student
  const handleSubmit = async (e) => {
    e.preventDefault();  // Prevent default form behavior

    const studentData = {  // Prepare student data
      username: e.target.username.value,
      email: e.target.email.value,
      password: e.target.password.value,
      university: selectedUniversity,
    };

    try {
      await registerStudent(studentData, headers);  // Register the student
      fireToast("Registered successfully", "success");  // Show success message
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {  // Handle unauthorized and resource access error
        logout();
        navigate("/");  
      }
      fireToast("Error registering student " + error.message, "error");  // Show error message
    }
  };

  // Form field configurations
  const formFields = [
    { label: "Username", name: "username", required: true },
    { label: "Email", name: "email", type: "email", required: true },
    { label: "Password", name: "password", type: "password", required: true },
  ];

  return (
    <Form
      title="Register Student"  // Form title
      buttonLabel="Register Student"  // Button label
      fields={formFields}  // Form fields
      onSubmit={handleSubmit}  // Form submit handler
    >
      {/* Autocomplete component for selecting a university */}
      <UniversityAutocomplete
        universities={universities}
        selectedUniversity={selectedUniversity}
        onUniversityChange={handleUniversityChange}
      />

      {/* Dialog for adding new university */}
      <AddUniversityDialog
        open={openDialog}
        onClose={() => setOpenDialog(false)}  // Close dialog
        newUniversity={newUniversity}  // New university data
        setNewUniversity={setNewUniversity}  // Update new university data
        onAddUniversity={handleAddUniversity}  // Handle university addition
      />
    </Form>
  );
};

export default RegisterStudent;
