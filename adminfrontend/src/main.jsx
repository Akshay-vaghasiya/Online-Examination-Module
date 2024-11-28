import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import React from 'react';
import App from './App';
import { CssBaseline, ThemeProvider, createTheme } from '@mui/material';
import { AuthProvider } from '../src/contexts/AuthContext';
import { ToastContainer } from 'react-toastify';
import { UserProvider } from './contexts/UserContext';
import { UniversityProvider } from './contexts/UniversityContext';
import { QuestionProvider } from './contexts/QuestionContext';

// Creating a theme for the Material-UI components
const theme = createTheme();

// Rendering the application into the DOM
createRoot(document.getElementById('root')).render(
  <StrictMode> {/* Enabling StrictMode for detecting issues */}
    <AuthProvider> {/* Providing authentication context to the application */}
      <UniversityProvider> {/* Providing university context to the application */}
        <QuestionProvider> {/* Providing question context to the application */}
          <UserProvider> {/* Providing user context to the application */}
            <ThemeProvider theme={theme}> {/* Applying the created theme to the application */}
              <CssBaseline /> {/* Normalize CSS styles across browsers */}
              <App />
            </ThemeProvider>
          </UserProvider>
        </QuestionProvider>
      </UniversityProvider>
    </AuthProvider>
    <ToastContainer /> {/* Configures toast notifications to display messages throughout the app */}
  </StrictMode>,
);
