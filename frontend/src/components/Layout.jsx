import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';
import { Box } from '@mui/material';

// Layout component to wrap the main layout structure with a navbar and content area
const Layout = () => {
  return (
    <>
      {/* Navbar component to display the navigation bar at the top */}
      <Navbar />
      
      {/* Main content area styled with padding, where nested routes will render */}
      <Box sx={{ padding: '16px' }}>
        {/* Outlet component renders the child route components */}
        <Outlet />
      </Box>
    </>
  );
};

export default Layout;
