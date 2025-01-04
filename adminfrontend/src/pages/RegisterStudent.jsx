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
import {
  Button,
  Typography,
  Box,
  Input,
  CircularProgress,
  Alert,
} from "@mui/material";

const RegisterStudent = () => {
  const [universities1, setUniversities] = useState([]); // State to store universities list
  const [selectedUniversity, setSelectedUniversity] = useState(null); // State for selected university
  const [openDialog, setOpenDialog] = useState(false); // Dialog state for adding a new university
  const [file, setFile] = useState(null);
  const [isUploading, setIsUploading] = useState(false);
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [newUniversity, setNewUniversity] = useState({
    // State for new university data
    universityName: "",
    address: "",
    contactEmail: "",
    contactPhone: "",
    websiteUrl: "",
  });

  const headers = AuthHeader(); // Authorization headers for API requests
  const navigate = useNavigate(); // Navigation hook for routing
  const { logout } = useAuth(); // Logout function from AuthContext
  const { getAllUniversities, registerUniversity } = universityService; // University service methods
  const { registerStudent, registerStudentExcel } = authService; // Auth service method to register a student
  const { universities, fetchUniversities } = useUniversityContext(); // University context

  let newheader = AuthHeader();
  newheader["Content-Type"] = "multipart/form-data";

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
    if (value?.substring(0, 5) === "Add: ") {
      // If user chooses to add a new university
      setOpenDialog(true); // Open the dialog to add university
    } else {
      setSelectedUniversity(value); // Set the selected university
    }
  };

  // Add new university
  const handleAddUniversity = async () => {
    if (newUniversity.universityName === "") {
      // Ensure university name is provided
      fireToast("Please enter university name", "error");
      return;
    }
    try {
      const addedUniversity = await registerUniversity(newUniversity, headers); // Register the new university
      setUniversities([...universities, addedUniversity?.universityName]); // Add new university to the list
      setSelectedUniversity(addedUniversity?.universityName); // Set as selected
      setOpenDialog(false); // Close dialog
      setNewUniversity({
        // Reset new university fields
        universityName: "",
        address: "",
        contactEmail: "",
        contactPhone: "",
        websiteUrl: "",
      });
      fireToast("University added successfully", "success"); // Show success message
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {
        // Handle unauthorized and resource access error
        logout();
        navigate("/");
      }
      fireToast("Error adding university " + error.message, "error"); // Show error message
    }
  };

  // Handle form submission for registering student
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent default form behavior

    const studentData = {
      // Prepare student data
      username: e.target.username.value,
      email: e.target.email.value,
      password: e.target.password.value,
      university: selectedUniversity,
      branch: e.target.branch.value,
      semester: e.target.semester.value,
    };

    try {
      await registerStudent(studentData, headers); // Register the student
      fireToast("Registered successfully", "success"); // Show success message
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {
        // Handle unauthorized and resource access error
        logout();
        navigate("/");
      }
      fireToast("Error registering student " + error.message, "error"); // Show error message
    }
  };

  // Handle file upload
  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setUploadSuccess(false);
    setErrorMessage("");
  };

  // Handle file upload
  const handleUpload = async () => {
    if (!file) {
      setErrorMessage("Please select a file first.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    setIsUploading(true);
    setErrorMessage("");
    setUploadSuccess(false);

    try {
      await registerStudentExcel(formData, newheader);
      setUploadSuccess(true);
      setFile(null);
    } catch (error) {
      console.error("Error uploading file:", error);
      setErrorMessage("Failed to upload file. Please try again.");
    } finally {
      setIsUploading(false);
    }
  };

  // Download sample file
  const handleDownload = () => {
    const filePath = `/Sample_Data.xlsx`;

    const link = document.createElement("a");
    link.href = filePath;
    link.setAttribute("download", "Sample_Data.xlsx");
    document.body.appendChild(link);
    link.click();
    link.remove();
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
    <Box
      sx={{
        mt: 8,
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
      }}
    >
      <Form
        title="Register Student" // Form title
        buttonLabel="Register Student" // Button label
        fields={formFields} // Form fields
        onSubmit={handleSubmit} // Form submit handler
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

      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        sx={{ mt: 4 }}
      >
        <Typography variant="h5" gutterBottom>
          Upload Student Data
        </Typography>
        <Input
          type="file"
          accept=".xlsx, .xls"
          onChange={handleFileChange}
          sx={{ mb: 2 }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleUpload}
          disabled={isUploading}
        >
          {isUploading ? (
            <CircularProgress size={24} sx={{ color: "white" }} />
          ) : (
            "Upload"
          )}
        </Button>

        <Button
          variant="contained"
          color="primary"
          sx={{ mt: 2 }}
          onClick={handleDownload}
        >
          Download Sample File
        </Button>

        {uploadSuccess && (
          <Alert severity="success" sx={{ mt: 2 }}>
            File uploaded successfully!
          </Alert>
        )}

        {errorMessage && (
          <Alert severity="error" sx={{ mt: 2 }}>
            {errorMessage}
          </Alert>
        )}
      </Box>
    </Box>
  );
};

export default RegisterStudent;
