import React from 'react';
import { styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { Button } from '@mui/material';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import LogoutIcon from '@mui/icons-material/Logout';
import { fireToast } from './fireToast';

// Define the width of the sidebar (drawer)
const drawerWidth = 240;

// Styled AppBar component with dynamic width based on the sidebar state
const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'isSidebarOpen', // Avoid passing 'isSidebarOpen' prop to the DOM
})(({ theme, isSidebarOpen }) => ({
  zIndex: theme.zIndex.drawer + 1, // Ensure the AppBar is above the drawer
  transition: theme.transitions.create(['width', 'margin'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen, // Transition when sidebar closes
  }),
  ...(isSidebarOpen && {
    marginLeft: drawerWidth, // Adjust the margin if the sidebar is open
    width: `calc(100% - ${drawerWidth}px)`, // Reduce width when sidebar is open
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen, // Transition when sidebar opens
    }),
  }),
}));

// Navbar component definition
const Navbar = ({ isSidebarOpen, handleDrawerToggle }) => {
  const { logout } = useAuth(); // Get logout function from AuthContext
  const navigate = useNavigate(); // For navigation after logout
  
  // Handle logout and navigate to the homepage
  const handleLogout = () => {
    fireToast("Logged out successfully", "success");
    logout();
    navigate('/'); // Redirect to the home page after logging out
  };

  return (
    <AppBar position="fixed" isSidebarOpen={isSidebarOpen}> {/* Custom AppBar with dynamic styling */}
      <Toolbar>
        {/* IconButton for toggling the sidebar */}
        <IconButton
          color="inherit"
          aria-label="open drawer"
          onClick={handleDrawerToggle} // Call handleDrawerToggle to open/close sidebar
          edge="start"
          sx={{ marginRight: 5, ...(isSidebarOpen && { display: 'none' }) }} // Hide when sidebar is open
        >
          <MenuIcon /> {/* Menu icon for toggling sidebar */}
        </IconButton>
        
        {/* Application title */}
        <Typography variant="h6" noWrap>
          Roima Intelligence
        </Typography>

        {/* Logout button with custom styling */}
        <Button
          variant="text"
          sx={{
            backgroundColor: '#3f51b5', // Primary color for the button
            color: '#ffffff', // White text color
            '&:hover': {
              backgroundColor: '#303f9f', // Darken color on hover
            },
            padding: '8px 16px',
            marginLeft: 'auto', // Push logout button to the right
          }}
          onClick={handleLogout} // Trigger logout on click
          startIcon={<LogoutIcon />}
        >
          Logout
        </Button>
      </Toolbar>
    </AppBar>
  );
}

export default Navbar; 