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

// Set drawer width for sidebar
const drawerWidth = 240;

// Style AppBar to adjust for sidebar toggle state
const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'isSidebarOpen',
})(({ theme, isSidebarOpen }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(['width', 'margin'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  // Adjust width and margin when sidebar is open
  ...(isSidebarOpen && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

// Navbar component with sidebar toggle and logout functionality
const Navbar = ({ isSidebarOpen, handleDrawerToggle }) => {
  const { logout } = useAuth();  // Access logout function from Auth context
  const navigate = useNavigate();  // Hook to navigate programmatically

  // Handle logout and navigate to home page
  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <AppBar position="fixed" isSidebarOpen={isSidebarOpen}>
      <Toolbar>
        {/* Menu icon button to toggle sidebar */}
        <IconButton
          color="inherit"
          aria-label="open drawer"
          onClick={handleDrawerToggle}
          edge="start"
          sx={{ marginRight: 5, ...(isSidebarOpen && { display: 'none' }) }}
        >
          <MenuIcon />
        </IconButton>
        
        {/* Title text */}
        <Typography variant="h6" noWrap>
          Mini variant drawer
        </Typography>

        {/* Logout button styled with custom colors and positioned to the right */}
        <Button 
          variant='text' 
          sx={{ 
            backgroundColor: '#3f51b5',
            color: '#ffffff',
            '&:hover': {
              backgroundColor: '#303f9f',
            },
            padding: '8px 16px',
            marginLeft: 'auto',
          }}  
          onClick={handleLogout}
        >
          Logout
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;