import React, { useState } from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Button,
  Drawer,
  List,
  ListItem,
  ListItemText,
  Box
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import AssignmentIcon from '@mui/icons-material/Assignment';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import LogoutIcon from '@mui/icons-material/Logout';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { fireToast } from './fireToast';

const Navbar = () => {
  // State to manage the drawer's open/closed status
  const [drawerOpen, setDrawerOpen] = useState(false);
  
  // State to track which navigation item is selected
  const [selectedNav, setSelectedNav] = useState('Exams');
  
  // Navigation hook from react-router-dom
  const navigate = useNavigate();
  
  // Retrieve the logout function from authentication context
  const { logout } = useAuth();

  // Function to toggle the drawer's open/closed state
  const toggleDrawer = (open) => (event) => {
    // Prevent drawer from toggling on tab or shift key actions
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setDrawerOpen(open);
  };

  // Define the items in the navigation bar
  const navItems = [
    { text: 'Exams', icon: <AssignmentIcon />, link: '/student/exams' },
    { text: 'Profile', icon: <AccountCircleIcon />, link: '/student/profile' },
    { text: 'Logout', icon: <LogoutIcon /> },
  ];

  // Function to handle navigation item click events
  const handleNavClick = (text, link) => {
    setSelectedNav(text); // Update selected navigation item
    console.log(`${text} clicked`);

    // Handle logout functionality
    if (link === '/student/logout' || text === 'Logout') {
       fireToast('Logged out successfully', 'success');
       logout(); // Call the logout function from context
       navigate('/'); // Redirect to the home page
    }
    navigate(link); // Navigate to the specified link
  };

  return (
    // Main AppBar component for the navigation bar
    <AppBar position="static" color="primary">
      <Toolbar>
        {/* Menu button for mobile view */}
        <IconButton
          edge="start"
          color="inherit"
          aria-label="menu"
          onClick={toggleDrawer(true)}
          sx={{ display: { xs: 'flex', md: 'none' } }}
        >
          <MenuIcon />
        </IconButton>
        
        {/* Platform title */}
        <Typography variant="h6" sx={{ flexGrow: 1, display: { md: 'block' } }}>
          Online Exam Platform
        </Typography>
        
        {/* Navigation buttons for desktop view */}
        <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
          {navItems.map((item) => (
            <Button
              key={item.text}
              color="inherit"
              sx={{
                // Style for the selected navigation item
                backgroundColor: selectedNav === item.text ? '#3B82F6' : 'inherit',
                color: selectedNav === item.text ? '#ffffff' : '#ffffff',
                '&:hover': {
                  backgroundColor: '#6495ed',
                },
                padding: '8px 16px',
                mx: '2px'
              }}
              startIcon={item.icon}
              onClick={() => handleNavClick(item.text, item?.link)}
            >
              {item.text}
            </Button>
          ))}
        </Box>
        
        {/* Drawer component for mobile view */}
        <Drawer anchor="left" open={drawerOpen} onClose={toggleDrawer(false)}>
          <Box
            sx={{ width: 250 }}
            role="presentation"
            onClick={toggleDrawer(false)}
            onKeyDown={toggleDrawer(false)}
          >
            {/* List of navigation items in the drawer */}
            <List>
              {navItems.map((item) => (
                <ListItem
                  button
                  key={item.text}
                  onClick={() => handleNavClick(item.text, item?.link)}
                  sx={{
                    // Highlight selected item in drawer
                    backgroundColor: selectedNav === item.text ? '#f3f4f7' : 'inherit',
                    color: '#000000',
                  }}
                >
                  <IconButton>{item.icon}</IconButton>
                  <ListItemText primary={item.text} />
                </ListItem>
              ))}
            </List>
          </Box>
        </Drawer>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;