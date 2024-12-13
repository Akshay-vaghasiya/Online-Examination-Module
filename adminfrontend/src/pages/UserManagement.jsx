import React, { useEffect, useState } from "react";
import {
  TextField,
  Select,
  MenuItem,
  CircularProgress,
  Typography,
} from "@mui/material";
import { useUserContext } from "../contexts/UserContext";
import TableComponent from "../components/TableComponent";
import { Delete, Edit, Quiz } from "@mui/icons-material";
import CustomDialogForm from "../components/CustomDialogForm";
import ConfirmationDialog from "../components/ConfirmationDialog";
import UniversityAutocomplete from "../components/UniversityAutocomplete";
import { useNavigate } from "react-router-dom";
import { useUniversityContext } from "../contexts/UniversityContext";

const UserManagement = () => {
  // Extracting user context actions and states
  const {
    filteredUsers,
    isLoading,
    isError,
    searchUsers,
    sortUsers,
    getUsers,
    updateUser,
    deleteUser,
  } = useUserContext();

  // Local state variables for handling input fields and dialog visibility
  const [searchTerm, setSearchTerm] = useState("");
  const [openDialog, setOpenDialog] = useState(false);
  const [deleteDialog, setDeleteDialog] = useState(false);
  const [sortOption, setSortOption] = useState("");
  const [universities1, setUniversities] = useState([]);
  const [selectedUniversity, setSelectedUniversity] = useState(null);
  const [updateData, setUpdateData] = useState({
    userid: "",
    username: "",
    email: "",
    role: "",
    branch: "",
    semester: 0,
  });
  const navigate = useNavigate();
  const {universities,fetchUniversities}=useUniversityContext();

  // Fetch users and universities on component mount
  useEffect(() => {
    if(universities.length===0) {
      fetchUniversities(navigate);
    }
    getUsers(navigate);
  }, []);

  // Extract university names from universities array
  useEffect(() => { 
    const universityNames = universities?.map(
      (university) => university.universityName
    );
      
    setUniversities(universityNames);
  }, [universities]);

  // Update search term and filter users based on search input
  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
    searchUsers(e.target.value);
  };

  // Update sort option and sort users accordingly
  const handleSort = (e) => {
    setSortOption(e.target.value);
    sortUsers(e.target.value);
  };

  // Update user data and close dialog upon success
  const handleUpdateUser = async () => {
    updateData.university = selectedUniversity;
    updateUser(updateData, navigate);
    setOpenDialog(false);
  };

  // Delete user and close dialog upon confirmation
  const handleDeleteUser = () => {
    deleteUser(updateData.userid, navigate);
    setDeleteDialog(false);
  };

  // Handle university selection in the autocomplete component
  const handleUniversityChange = (event, value) => {
    setSelectedUniversity(value);
  };

  // Loading spinner display while fetching data
  if (isLoading) {
    return <CircularProgress />;
  }

  // Error message display if user loading fails
  if (isError) {
    return (
      <Typography color="error">
        An error occurred while loading users. Please try again later.
      </Typography>
    );
  }

  // Define table columns and row actions for user management table
  const columns = ["UserID", "Username", "Email", "Role", "University", "Branch", "Semester"];
  // Define data columns name for the table
  const datacolumns = ["userid", "username", "email", "role", "university", "branch", "semester"];
  const actions = [
    {
      label: "Update Profile",
      color: "primary",
      icon: <Edit />,
      handler: (user) => {  
        setUpdateData(user);
        setOpenDialog(true);
        setSelectedUniversity(user.university);
      },
    },
    {
      label: "Delete User",
      color: "error",
      icon: <Delete />,
      handler: (user) => {
        setUpdateData(user);
        setDeleteDialog(true);
      },
    },
    {
      label: "Given Exams",
      color: "info",
      icon: <Quiz />,
      handler: (user) => console.log("Given", user),
    },
  ];

  // Fields for the user profile update dialog form
  const userFields = [
    { label: "User Id", name: "userid", required: true, disabled: true },
    { label: "Username", name: "username", required: true },
    { label: "Email", name: "email", type: "email", required: true },
    { label: "Role", name: "role", required: true },
    { label: "Branch", name: "branch" },
    { label : "Semester", name : "semester" },
  ];

  return (
    <div>
      <Typography variant="h4" align="center" gutterBottom>
        User Management
      </Typography>
      
      {/* Search and sort controls */}
      <div style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}>
        <TextField
          label="Search"
          variant="outlined"
          value={searchTerm}
          onChange={handleSearch}
          sx={{ width: "25rem" }}
        />
        <Select
          value={sortOption}
          onChange={handleSort}
          displayEmpty
          variant="outlined"
        >
          <MenuItem value="">Sort By</MenuItem>
          <MenuItem value="username">Username</MenuItem>
          <MenuItem value="email">Email</MenuItem>
          <MenuItem value="university">University</MenuItem>
        </Select>
      </div>

      {/* User table displaying user data */}
      <TableComponent
        columns={columns}
        data={filteredUsers}
        actions={actions}
        datacolumns={datacolumns}
      />

      {/* Dialog form for updating user profile */}
      <CustomDialogForm
        open={openDialog}
        onClose={setOpenDialog}
        formData={updateData}
        setFormData={setUpdateData}
        onSubmit={handleUpdateUser}
        title="Update User Profile"
        submitButtonText="Save Changes"
        fields={userFields}
      >
        <UniversityAutocomplete
          universities={universities1}
          value={selectedUniversity}
          onUniversityChange={handleUniversityChange}
        />
      </CustomDialogForm>

      {/* Confirmation dialog for deleting user */}
      <ConfirmationDialog
        open={deleteDialog}
        title="Confirm Deletion"
        message="Are you sure you want to delete this user?"
        confirmText="delete"
        cancelText="cancel"
        onConfirm={handleDeleteUser}
        onCancel={setDeleteDialog}
      />
    </div>
  );
};

export default UserManagement;