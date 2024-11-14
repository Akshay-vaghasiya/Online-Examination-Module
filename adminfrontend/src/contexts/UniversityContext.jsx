import React, { createContext, useContext, useReducer } from "react";
import reducer from "../reducers/universityReducer";
import universityService from "../services/universityService";
import { fireToast } from "../components/fireToast";
import AuthHeader from "../Helper/AuthHeader";
import { useAuth } from "./AuthContext";

// Initial state for the university data
const initialState = {
  universities: [],             // Stores all universities
  filteredUniversities: [],      // Stores filtered universities based on search query
  isLoading: false,              // Indicates if data is currently loading
  isError: false,                // Indicates if there was an error during fetching
};

// Create the UniversityContext to manage and share university data across components
const UniversityContext = createContext(initialState);

// UniversityProvider component that provides university state and actions to its children
const UniversityProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);  // Reducer to manage state based on actions
  const headers = AuthHeader();  // Authorization header for API requests
  const { logout } = useAuth();  // Logout function from AuthContext

  // Fetch all universities and handle errors if any
  const fetchUniversities = async (navigate) => {
    dispatch({ type: "FETCH_UNIVERSITIES" });                  
    try {
      const universities = await universityService.getAllUniversities(headers);
      dispatch({ type: "FETCH_UNIVERSITIES_SUCCESS", payload: universities });
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {
        logout();
        navigate("/");
      } 
      dispatch({ type: "FETCH_UNIVERSITIES_FAILURE" });        
      fireToast("Failed to fetch universities", "error");      
    }
  };

  // Register a new university and handle any errors
  const registerUniversity = async (university, navigate) => {
    try {
      const newUniversity = await universityService.registerUniversity(university, headers);
      dispatch({ type: "REGISTER_UNIVERSITY", payload: newUniversity });
      fireToast("University registered successfully", "success"); 
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {
        logout();
        navigate("/");
      }
      fireToast("Failed to register university", "error");      
    }
  };

  // Update university information and handle errors
  const updateUniversity = async (university, navigate) => {
    try {
      const updatedUniversity = await universityService.updateUniversity(university.universityId, university, headers);
      dispatch({ type: "UPDATE_UNIVERSITY", payload: updatedUniversity });
      fireToast("University updated successfully", "success"); 
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {
        logout();
        navigate("/");
      }
      fireToast("Failed to update university", "error");       
    }
  };

  // Delete a university and handle any errors
  const deleteUniversity = async (universityId, navigate) => {
    try {
      await universityService.deleteUniversity(universityId, headers);
      dispatch({ type: "DELETE_UNIVERSITY", payload: universityId });
      fireToast("University deleted successfully", "success");  
    } catch (error) {
      if (error.response.status === 401 || error.response.status === 403) {
        logout();
        navigate("/");
      }
      fireToast("Failed to delete university", "error");       
    }
  };

  // Filter universities based on a search query
  const searchUniversities = (query) => {
    dispatch({ type: "SEARCH_UNIVERSITIES", payload: query });
  };

  // Provide university state and action methods to children components
  return (
    <UniversityContext.Provider
      value={{
        ...state,
        fetchUniversities,
        registerUniversity,
        updateUniversity,
        deleteUniversity,
        searchUniversities,
      }}
    >
      {children}
    </UniversityContext.Provider>
  );
};

// Custom hook to use the UniversityContext in other components
const useUniversityContext = () => {
  return useContext(UniversityContext);
};

export { useUniversityContext, UniversityProvider };