import React from 'react';
import { styled, useTheme } from '@mui/material/styles';
import MuiDrawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import { Description, PeopleAlt, PersonAdd, Quiz } from '@mui/icons-material';
import { Link } from 'react-router-dom';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';

// Set the width of the sidebar drawer
const drawerWidth = 240;

// Define styles for when the sidebar is open
const openedMixin = (theme) => ({
  width: drawerWidth,
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: 'hidden',
});

// Define styles for when the sidebar is closed
const closedMixin = (theme) => ({
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: 'hidden',
  width: `calc(${theme.spacing(7)} + 1px)`, // Closed width
  [theme.breakpoints.up('sm')]: {
    width: `calc(${theme.spacing(8)} + 1px)`,
  },
});

// Styled Drawer component that applies styles based on 'isOpen' prop
const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'isOpen' })(
  ({ theme, isOpen }) => ({
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
    boxSizing: 'border-box',
    ...(isOpen && {
      ...openedMixin(theme),
      '& .MuiDrawer-paper': openedMixin(theme), // Open state styles for paper
    }),
    ...(!isOpen && {
      ...closedMixin(theme),
      '& .MuiDrawer-paper': closedMixin(theme), // Closed state styles for paper
    }),
  }),
);

// Sidebar functional component with props for drawer state and callbacks
const Sidebar = ({ isOpen, onClose, selectedIndex, onItemClick }) => {
  const theme = useTheme(); // Access the theme for dynamic styling

  // Define menu items with icons and links for navigation
  const menuItems = [
    { text: 'Student Register', icon: <PersonAdd />, link: '/admin/register-student' },
    { text: 'Admin Register', icon: <PersonAdd />, link: '/admin/register-admin' },
    { text: 'User Management', icon: <PeopleAlt />, link: '/admin/user-management' },
    { text: 'Questions', icon: <Quiz />, link: '/admin/questions' },
    { text: 'Exams', icon: <Description />, link: '/admin/exams' },
  ];

  return (
    <Drawer variant="permanent" isOpen={isOpen}>
      {/* Drawer header with close button */}
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end', padding: '10px 8px' }}>
        <IconButton onClick={onClose}>
          {/* Toggle drawer direction based on theme (e.g., LTR or RTL) */}
          {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
        </IconButton>
      </div>
      <Divider />
      
      {/* Render menu items */}
      <List>
        {menuItems.map((item, index) => (
          <ListItem
            key={item.text}
            disablePadding
            sx={{ display: 'block' }}
            component={Link} // Use react-router Link for navigation
            to={item.link}
          >
            <ListItemButton
              selected={selectedIndex === index} // Highlight selected item
              onClick={() => onItemClick(index)} // Update selected item on click
              sx={{
                minHeight: 48,
                px: 2.5,
                color: 'black',
                ...(selectedIndex === index && {
                  backgroundColor: theme.palette.action.selected, // Highlight background for selected item
                }),
                justifyContent: isOpen ? 'initial' : 'center', // Adjust text alignment based on drawer state
              }}
            >
              {/* Icon for each menu item */}
              <ListItemIcon
                sx={{
                  minWidth: 0,
                  justifyContent: 'center',
                  color: 'black',
                  mr: isOpen ? 3 : 'auto', // Adjust margin based on drawer state
                }}
              >
                {item.icon}
              </ListItemIcon>
              {/* Text label for each menu item, hidden when drawer is closed */}
              <ListItemText
                primary={item.text}
                sx={{ color: 'black', opacity: isOpen ? 1 : 0 }}
              />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Drawer>
  );
}

export default Sidebar;
