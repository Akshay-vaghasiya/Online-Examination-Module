import React, { useEffect, useState } from "react";
import {
  CircularProgress,
  TextField,
  Typography,
} from "@mui/material";
import TableComponent from "../components/TableComponent"; 
import { Delete, Edit } from "@mui/icons-material"; 
import CustomDialogForm from "../components/CustomDialogForm"; 
import ConfirmationDialog from "../components/ConfirmationDialog";
import { useUniversityContext } from "../contexts/UniversityContext";
import { useNavigate } from "react-router-dom";

const UniversityManagement = () => {
  // Context methods and state for universities
  const { isLoading, isError, universities, filteredUniversities, updateUniversity, deleteUniversity, searchUniversities, fetchUniversities } = useUniversityContext();

  // State for managing dialog visibility and selected university data
  const [openDialog, setOpenDialog] = useState(false);
  const [deleteDialog, setDeleteDialog] = useState(false);
  const [selectedUniversity, setSelectedUniversity] = useState({
    universityId: "",
    universityName: "",
    address: "",
    websiteUrl: "",
    contactEmail: "",
    contactPhone: "",
  });
  const [searchTerm, setSearchTerm] = useState("");
  const navigate = useNavigate(); 

  // Fetch universities on component mount
  useEffect(() => {
    if (universities.length === 0) {
      fetchUniversities();
    }
  }, []);
  
  // Handle search functionality
  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
    searchUniversities(event.target.value);
  };

  // Submit updated university details
  const handleUpdateUniversity = () => {
    updateUniversity(selectedUniversity, navigate);
    setOpenDialog(false);
  };

  // Confirm and delete a university
  const handleDeleteUniversity = (universityId) => {
    deleteUniversity(universityId, navigate);
    setDeleteDialog(false);
  };

  // Table column definitions and actions for edit/delete
  const columns = ["University ID", "Name", "Address", "Web URL", "Contact Email", "Contact Number"];
  const datacolumns = ["universityId", "universityName", "address", "websiteUrl", "contactEmail", "contactPhone"];
  const actions = [
    {
      label: "Edit",
      color: "primary",
      icon: <Edit />,
      handler: (university) => {
        setSelectedUniversity(university);
        setOpenDialog(true);
      },
    },
    {
      label: "Delete",
      color: "secondary",
      icon: <Delete />,
      handler: (university) => {
        setSelectedUniversity(university);
        setDeleteDialog(true);
      },
    },
  ];

  // Fields for the university update form
  const universityFields = [
    { label: "University Id", name: "universityId", disabled: true },
    { label: "University Name", name: "universityName", required: true },
    { label: "Address", name: "address", type: "textarea"},
    { label: "Contact Email", name: "contactEmail", type: "email"},
    { label: "Contact Number", name: "contactPhone"},
    { label: "Website URL", name: "websiteUrl", type: "url"},
  ];

  // Display loader or error message if applicable
  if (isLoading) {
    return <CircularProgress />;
  }

  if (isError) {
    return (
      <Typography color="error">
        Error loading universities. Please try again later.
      </Typography>
    );
  }

  return (
    <div>
      <Typography variant="h4" align="center" gutterBottom>
        University Management
      </Typography>

      {/* Search input */}
      <TextField
          label="Search"
          variant="outlined"
          value={searchTerm}
          onChange={handleSearch}
          sx={{ width: "25rem", marginBottom: "1rem" }}
      />

      {/* Table of universities */}
      <TableComponent columns={columns} data={filteredUniversities} actions={actions} datacolumns={datacolumns} />
      
      {/* Update university dialog */}
      <CustomDialogForm
        open={openDialog}
        onClose={setOpenDialog}
        formData={selectedUniversity}
        setFormData={setSelectedUniversity}
        onSubmit={handleUpdateUniversity}
        title="Update University Details"
        submitButtonText="Save Changes"
        fields={universityFields}
      />

      {/* Confirmation dialog for deletion */}
      <ConfirmationDialog
        open={deleteDialog}
        title="Confirm Deletion"
        message={`Are you sure you want to delete "${selectedUniversity?.universityName}" university?`}
        confirmText="Delete"
        cancelText="Cancel"
        onConfirm={() => handleDeleteUniversity(selectedUniversity.universityId)}
        onCancel={() => setDeleteDialog(false)}
      />
    </div>
  );
};

export default UniversityManagement;
