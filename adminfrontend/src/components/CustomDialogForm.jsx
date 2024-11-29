import React from "react";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Button,
  MenuItem,
} from "@mui/material";

// CustomDialogForm is a reusable dialog component for displaying forms.
// It dynamically generates form fields based on the `fields` prop, and handles
// data submission and cancellation.
const CustomDialogForm = ({
  open, // Boolean to control dialog visibility
  onClose, // Function to handle dialog close
  formData, // State to hold form data
  setFormData, // Function to update form data
  onSubmit, // Function to handle form submission
  title = "Dialog Form", // Title of the dialog
  submitButtonText = "Submit", // Text for the submit button
  fields = [], // Array of field configurations for the form
  children, // Additional content to render inside the dialog
}) => {
  // Handle form field value changes
  const handleChange = (field) => (event) => {
    setFormData({ ...formData, [field]: event.target.value });
  };

  return (
    <Dialog open={open} onClose={() => onClose(false)} maxWidth="sm" fullWidth>
      <DialogTitle align="center">{title}</DialogTitle>
      <DialogContent>
        {/* Render form fields dynamically based on the fields array */}
        {fields.map(({ label, name, type, required = false, options, rows, disabled }) => {
          if (type === "select") {
            // Render a dropdown field
            return (
              <TextField
                key={name}
                select
                label={label}
                fullWidth
                margin="normal"
                required={required}
                value={formData[name] || ""}
                onChange={handleChange(name)}
                disabled={disabled}
              >
                {options.map((option) => (
                  <MenuItem key={option.value} value={option.value}>
                    {option.label}
                  </MenuItem>
                ))}
              </TextField>
            );
          } else if (type === "textarea") {
            // Render a multiline text area
            return (
              <TextField
                key={name}
                label={label}
                fullWidth
                margin="normal"
                required={required}
                multiline
                rows={rows || 4}
                value={formData[name] || ""}
                onChange={handleChange(name)}
                disabled={disabled}
              />
            );
          } else {
            // Render a regular text field
            return (
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
            );
          }
        })}

        {children /* Render any additional children components */}
      </DialogContent>
      <DialogActions>
        <Button onClick={() => onClose(false)} color="secondary">
          Cancel {/* Button to cancel/close the dialog */}
        </Button>
        <Button onClick={onSubmit} color="primary">
          {submitButtonText} {/* Submit button with customizable text */}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CustomDialogForm; 
