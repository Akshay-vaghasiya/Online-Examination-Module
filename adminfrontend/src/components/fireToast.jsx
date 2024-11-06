import { Bounce, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

// Function to trigger toast notifications
export const fireToast = (message, type) => {
    // Check the type of message and display corresponding toast notification
    if (type === "success") {
        console.log(message); // Log success message to the console
        toast.success(message, { // Display success toast with specific options
            position: "top-right", // Position toast at the top-right of the screen
            autoClose: 3000, // Close the toast automatically after 3000 ms
            hideProgressBar: false, // Show progress bar in the toast
            closeOnClick: true, // Close toast when clicked
            pauseOnHover: true, // Pause auto-close when hovered
            draggable: true, // Allow toast to be dragged
            progress: undefined, // Use default progress bar behavior
            theme: "light", // Set the theme of the toast to light
            transition: Bounce, // Use bounce animation for showing the toast
        });
    } else if (type === "error") {
        toast.error(message, { // Display error toast with similar options
            position: "top-right",
            autoClose: 3000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
            transition: Bounce,
        });
    } else {
        // Display a default toast for unspecified types
        toast(message, {
            position: "top-right",
            autoClose: 3000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
            transition: Bounce,
        });
    }
};
