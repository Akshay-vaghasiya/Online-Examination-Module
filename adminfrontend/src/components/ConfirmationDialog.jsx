import React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';

// A reusable confirmation dialog component for user prompts
const ConfirmationDialog = ({
  open,          // Boolean to control dialog visibility
  title,         // Title of the dialog (optional)
  message,       // Message displayed in the dialog
  confirmText = "Yes", // Text for the confirm button (defaults to "Yes")
  cancelText = "No",   // Text for the cancel button (defaults to "No")
  onConfirm,     // Function called on confirm action
  onCancel       // Function called on cancel action
}) => {
  return (
    <Dialog open={open} onClose={onCancel}>
      {title && <DialogTitle>{title}</DialogTitle>} {/* Display title if provided */}
      <DialogContent>
        <DialogContentText>{message}</DialogContentText> {/* Display message */}
      </DialogContent>
      <DialogActions>
        <Button onClick={() => onCancel(false)} color="secondary">
          {cancelText} {/* Cancel button */}
        </Button>
        <Button onClick={onConfirm} color="primary" autoFocus>
          {confirmText} {/* Confirm button */}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmationDialog; 
