import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import React from 'react';
import App from './App';
import { CssBaseline, ThemeProvider, createTheme } from '@mui/material';
import { AuthProvider } from '../src/contexts/AuthContext';
import { ToastContainer } from 'react-toastify';

// Creating a theme for the Material-UI components
const theme = createTheme();

// Rendering the application into the DOM
createRoot(document.getElementById('root')).render(
  <StrictMode> {/* Enabling StrictMode for detecting issues */}
    <AuthProvider> {/* Providing authentication context to the application */}
      <ThemeProvider theme={theme}> {/* Applying the created theme to the application */}
        <CssBaseline /> {/* Normalize CSS styles across browsers */}
        <App />
      </ThemeProvider>
    </AuthProvider>
    <ToastContainer /> {/* Configures toast notifications to display messages throughout the app */}
  </StrictMode>,
);
