import React, { useState } from 'react';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import Navbar from './Navbar';
import Sidebar from './Sidebar';
import { Outlet } from 'react-router-dom';

// DashboardLayout component to manage the overall layout including the Navbar, Sidebar, and main content area
const DashboardLayout = ({ children }) => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false); // State to control sidebar open/close
  const [selectedIndex, setSelectedIndex] = useState(0); // State to track which sidebar item is selected

  // Toggle function to open or close the sidebar
  const handleDrawerToggle = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  return (
    <Box sx={{ display: 'flex' }}> {/* Main container with flex layout */}
      <CssBaseline /> {/* Reset baseline styles for consistent look */}
      
      {/* Navbar component with props to control sidebar state */}
      <Navbar isSidebarOpen={isSidebarOpen} handleDrawerToggle={handleDrawerToggle} />
      
      {/* Sidebar component with props to manage its state and active item */}
      <Sidebar 
        isOpen={isSidebarOpen} 
        onClose={handleDrawerToggle} 
        selectedIndex={selectedIndex} 
        onItemClick={(index) => setSelectedIndex(index)} 
      />
      
      {/* Main content area styled to grow and accommodate children components */}
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <div style={{ minHeight: '64px' }} /> {/* Spacer to offset Navbar height */}
        <Outlet /> {/* Outlet for rendering nested routes or main content */}
      </Box>
    </Box>
  );
}

export default DashboardLayout;
