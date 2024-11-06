import React from "react";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Button,
} from "@mui/material";

// Component to add a new university, with fields for university details
const AddUniversityDialog = ({ open, onClose, newUniversity, setNewUniversity, onAddUniversity }) => {
  
  // Updates the state for a specific field in `newUniversity`
  const handleChange = (field) => (event) => {
    setNewUniversity({ ...newUniversity, [field]: event.target.value });
  };

  // Field configuration array for dynamically rendering text fields
  const fields = [
    { label: "University Name", name: "universityName", required: true },
    { label: "Address", name: "address" },
    { label: "Contact Email", name: "contactEmail", type: "email" },
    { label: "Contact Phone", name: "contactPhone" },
    { label: "Website URL", name: "websiteUrl" },
  ];

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Add University</DialogTitle>
      <DialogContent>
        {/* Render text fields based on the fields configuration */}
        {fields.map(({ label, name, type, required }) => (
          <TextField
            key={name}
            label={label}
            fullWidth
            margin="dense"
            required={required || false}
            type={type || "text"}
            value={newUniversity[name] || ""}
            onChange={handleChange(name)}
          />
        ))}
      </DialogContent>
      <DialogActions>
        {/* Button to cancel and close the dialog */}
        <Button onClick={() => onClose(false)} color="secondary">
          Cancel
        </Button>
        {/* Button to trigger the add university function */}
        <Button onClick={onAddUniversity} color="primary">
          Add
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddUniversityDialog;
