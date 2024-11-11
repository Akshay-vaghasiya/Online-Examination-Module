import React from "react";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Button,
} from "@mui/material";

// CustomDialogForm is a reusable dialog component for displaying forms.
// It dynamically generates form fields based on the `fields` prop, and handles
// data submission and cancellation.
const CustomDialogForm = ({ 
  open,               // Controls dialog visibility
  onClose,            // Function to close the dialog
  formData,           // Form data object holding input values
  setFormData,        // Setter function to update form data
  onSubmit,           // Function triggered on form submission
  title = "Dialog Form", // Dialog title (default: "Dialog Form")
  submitButtonText = "Submit", // Submit button text (default: "Submit")
  fields = [],        // Array of field objects defining form inputs
  children            // Optional additional elements in the dialog content
}) => {

  // Updates formData for a specific field based on user input
  const handleChange = (field) => (event) => {
    setFormData({ ...formData, [field]: event.target.value });
  };

  return (
    <Dialog open={open} onClose={() => onClose(false)}>
      <DialogTitle align="center">{title}</DialogTitle> {/* Dialog title */}
      <DialogContent>
        {/* Render each field as a TextField based on the fields prop */}
        {fields.map(({ label, name, type = "text", required = false, disabled }) => (
          <TextField
            key={name}
            label={label}
            fullWidth
            margin="normal"
            required={required}
            type={type}
            value={formData[name] || ""}
            onChange={handleChange(name)}
            disabled={disabled}
          />
        ))}

        {children} {/* Render additional elements passed as children */}
      </DialogContent>
      <DialogActions>
        <Button onClick={() => onClose(false)} color="secondary">
          Cancel {/* Cancel button */}
        </Button>
        <Button onClick={onSubmit} color="primary">
          {submitButtonText} {/* Submit button */}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CustomDialogForm;