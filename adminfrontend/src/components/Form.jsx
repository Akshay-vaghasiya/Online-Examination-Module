import React from "react";
import { Box, Button, TextField, Typography } from "@mui/material";

// Reusable form component that renders a form with title, button label, input fields, and custom children
const Form = ({ title, buttonLabel, fields, onSubmit, children }) => {
  return (
    // Form container with maximum width and centered alignment
    <Box component="form" onSubmit={onSubmit} sx={{ maxWidth: 400, mx: "auto", mt: 4 }}>
      
      {/* Form title */}
      <Typography variant="h4" component="h1" gutterBottom>
        {title}
      </Typography>
      
      {/* Dynamically renders each input field passed in the `fields` prop */}
      {fields.map((field, index) => (
        <TextField
          key={index}
          margin="normal"
          fullWidth
          label={field.label}
          type={field.type || "text"}
          name={field.name}
          value={field.value}
          onChange={field.onChange}
          required={field.required !== false} // Fields are required by default unless specified
          autoComplete="true"
        />
      ))}

      {/* Custom components or additional content can be passed as children */}
      {children}

      {/* Submit button with customizable label */}
      <Button type="submit" fullWidth variant="contained" color="primary" sx={{ mt: 3, height: 56 }}>
        {buttonLabel}
      </Button>
    </Box>
  );
};

export default Form;