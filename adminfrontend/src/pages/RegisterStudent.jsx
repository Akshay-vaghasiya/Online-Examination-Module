import React, { useState, useEffect } from "react";
import AuthHeader from "../Helper/AuthHeader";
import { fireToast } from "../components/fireToast";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import UniversityAutocomplete from "../components/UniversityAutocomplete";
import Form from "../components/Form";
import universityService from "../services/universityService";
import authService from "../services/authService";
import CustomDialogForm from "../components/CustomDialogForm";
import { useUniversityContext } from "../contexts/UniversityContext";

const RegisterStudent = () => {
  const [universities1, setUniversities] = useState([]);  // State to store universities list
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
  const {universities, fetchUniversities} = useUniversityContext(); // University context

  // Fetch list of universities if universities list is empty on component mount
  useEffect(() => {
    if (universities.length === 0) {
      fetchUniversities();
    }
  }, []);

  // Extract university names from universities list
  useEffect(() => { 
    const universityNames = universities?.map(
      (university) => university.universityName
    );
      
    setUniversities(universityNames);
  }, [universities]);

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
      branch: e.target.branch.value,
      semester: e.target.semester.value,
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
    { label: "Branch", name: "branch" },
    { label: "Semester", name: "semester", type: "number" },
  ];
 
  // University field configurations
  const universityFields = [
    { label: "University Name", name: "universityName", required: true },
    { label: "Address", name: "address" },
    { label: "Contact Email", name: "contactEmail", type: "email" },
    { label: "Contact Phone", name: "contactPhone" },
    { label: "Website URL", name: "websiteUrl" },
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
        universities={universities1}
        selectedUniversity={selectedUniversity}
        onUniversityChange={handleUniversityChange}
      />

      {/* Dialog for adding new university */}
      <CustomDialogForm
        open={openDialog}
        onClose={setOpenDialog}
        formData={newUniversity}
        setFormData={setNewUniversity}
        onSubmit={handleAddUniversity}
        title="Add University"
        submitButtonText="Add"
        fields={universityFields}
      />
    </Form>
  );
};

export default RegisterStudent;
