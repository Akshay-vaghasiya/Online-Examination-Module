import { Bounce, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

// Function to trigger toast notifications
export const fireToast = (message, type) => {
    // Display the toast notification with the specified message and type
    toast(message, {
        type: type, // Type of toast (e.g., "success", "error", "info", "warning")
        position: "top-right", // Position of the toast on the screen
        autoClose: 3000, // Automatically close the toast after 3 seconds
        hideProgressBar: false, // Show the progress bar in the toast
        closeOnClick: true, // Allow the toast to close when clicked
        pauseOnHover: true, // Pause the auto-close timer when hovering over the toast
        draggable: true, // Enable drag-and-drop to dismiss the toast
        progress: undefined, // Progress bar will automatically calculate progress
        theme: "light", // Use the light theme for the toast
        transition: Bounce, // Animation style for toast entry/exit
    });
};