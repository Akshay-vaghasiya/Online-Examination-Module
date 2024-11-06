import React from "react";
import { Autocomplete, TextField } from "@mui/material";

// Autocomplete component for selecting or adding a new university
const UniversityAutocomplete = ({ universities, selectedUniversity, onUniversityChange }) => (
  <Autocomplete
    // Selected university value
    value={selectedUniversity}
    // Callback for when an option is selected or input changes
    onChange={onUniversityChange}
    // List of university options for autocomplete
    options={universities}
    // Display the option label (university name) or empty string if undefined
    getOptionLabel={(option) => option || ""}
    // Render the input field with a "University" label and required status
    renderInput={(params) => <TextField {...params} label="University" margin="normal" required />}
    // Allows the user to enter a custom option that’s not in the list
    freeSolo
    // Custom filter for options that includes "Add: <input>" if no match is found
    filterOptions={(options, params) => {
      const filtered = options.filter((option) =>
        option.toLowerCase().includes(params.inputValue.toLowerCase())
      );
      // Adds an "Add: <input>" option if no existing options match the input
      if (params.inputValue !== "" && !filtered.length) {
        filtered.push(`Add: ${params.inputValue}`);
      }
      return filtered;
    }}
  />
);

export default UniversityAutocomplete;