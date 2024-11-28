import React, { createContext, useContext, useReducer } from "react";
import reducer from "../reducers/questionReducer";
import questionService from "../services/questionService"; 
import { fireToast } from "../components/fireToast"; 
import AuthHeader from "../Helper/AuthHeader"; 
import { useAuth } from "./AuthContext"; 

// Initial state for the QuestionContext
const initialState = {
  questions: [], // Array to store all questions
  filteredquestions: [], // Array to store filtered questions based on search
  isLoading: false, // Boolean to indicate loading state
  isError: false, // Boolean to indicate if there is an error
};

// Create a context for questions
const QuestionContext = createContext(initialState);

// Provider component to wrap the app with QuestionContext
const QuestionProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState); // Use reducer for state management
  const headers = AuthHeader(); // Get authorization headers
  const { logout } = useAuth(); // Get logout function from AuthContext

  // Function to fetch all MCQ questions from the backend
  const fetchMcqQuestions = async (navigate) => {
    dispatch({ type: "FETCH_MCQ_QUESTIONS" }); // Dispatch loading state
    try {
      const mcqQuestions = await questionService.getMcqQuestions(headers); // Fetch data from API
      dispatch({ type: "FETCH_MCQ_QUESTIONS_SUCCESS", payload: mcqQuestions }); // Dispatch success state
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      dispatch({ type: "FETCH_MCQ_QUESTIONS_FAILURE" }); // Dispatch failure state
      fireToast("Failed to fetch MCQ questions", "error"); // Show error toast
    }
  };

  // Function to fetch all coding questions from the backend
  const fetchCodingQuestions = async (navigate) => {
    dispatch({ type: "FETCH_CODING_QUESTIONS" }); // Dispatch loading state
    try {
      const codingQuestions = await questionService.getCodingQuestions(headers); // Fetch data from API
      dispatch({ type: "FETCH_CODING_QUESTIONS_SUCCESS", payload: codingQuestions }); // Dispatch success state
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      dispatch({ type: "FETCH_CODING_QUESTIONS_FAILURE" }); // Dispatch failure state
      fireToast("Failed to fetch coding questions", "error"); // Show error toast
    }
  };

  // Function to add a new MCQ question
  const registerMcqQuestion = async (mcqQuestion, navigate) => {
    try {
      const newMcqQuestion = await questionService.addMcqQuestion(mcqQuestion, headers); // API call to add MCQ
      dispatch({ type: "REGISTER_MCQ_QUESTION", payload: newMcqQuestion }); // Dispatch success action
      fireToast("MCQ question added successfully", "success"); // Show success toast
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      fireToast("Failed to add MCQ question", "error"); // Show error toast
    }
  };

  // Function to add a new coding question
  const registerCodingQuestion = async (codingQuestion, navigate) => {
    try {
      const newCodingQuestion = await questionService.addCodingQuestion(codingQuestion, headers); // API call to add coding question
      dispatch({ type: "REGISTER_CODING_QUESTION", payload: newCodingQuestion }); // Dispatch success action
      fireToast("Coding question added successfully", "success"); // Show success toast
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      fireToast("Failed to add coding question", "error"); // Show error toast
    }
  };

  // Function to update an existing MCQ question
  const updateMcqQuestion = async (mcqQuestion, navigate) => {
    try {
      const updatedMcqQuestion = await questionService.updateMcqQuestion(
        mcqQuestion.id,
        mcqQuestion,
        headers
      ); // API call to update MCQ
      dispatch({ type: "UPDATE_MCQ_QUESTION", payload: updatedMcqQuestion }); // Dispatch success action
      fireToast("MCQ question updated successfully", "success"); // Show success toast
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      fireToast("Failed to update MCQ question", "error"); // Show error toast
    }
  };

  // Function to update an existing coding question
  const updateCodingQuestion = async (codingQuestion, navigate) => {
    try {
      const updatedCodingQuestion = await questionService.updateCodingQuestion(
        codingQuestion.id,
        codingQuestion,
        headers
      ); // API call to update coding question
      dispatch({ type: "UPDATE_CODING_QUESTION", payload: updatedCodingQuestion }); // Dispatch success action
      fireToast("Coding question updated successfully", "success"); // Show success toast
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      fireToast("Failed to update coding question", "error"); // Show error toast
    }
  };

  // Function to delete an MCQ question
  const deleteMcqQuestion = async (questionId, navigate) => {
    try {
      await questionService.deleteMcqQuestion(questionId, headers); // API call to delete MCQ
      dispatch({ type: "DELETE_MCQ_QUESTION", payload: questionId }); // Dispatch delete action
      fireToast("MCQ question deleted successfully", "success"); // Show success toast
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      fireToast("Failed to delete MCQ question", "error"); // Show error toast
    }
  };

  // Function to delete a coding question
  const deleteCodingQuestion = async (questionId, navigate) => {
    try {
      await questionService.deleteCodingQuestion(questionId, headers); // API call to delete coding question
      dispatch({ type: "DELETE_CODING_QUESTION", payload: questionId }); // Dispatch delete action
      fireToast("Coding question deleted successfully", "success"); // Show success toast
    } catch (error) {
      handleAuthError(error, navigate); // Handle authentication errors
      fireToast("Failed to delete coding question", "error"); // Show error toast
    }
  };

  // Function to filter questions based on a search query
  const searchQuestions = (query) => {
    dispatch({ type: "SEARCH_QUESTIONS", payload: query }); // Dispatch search action
  };

  // Handle authentication errors (e.g., unauthorized access)
  const handleAuthError = (error, navigate) => {
    if (error?.response?.status === 401 || error?.response?.status === 403) {
      logout(); // Logout the user
      navigate("/"); // Redirect to home page
      fireToast("Unauthorized access", "error"); // Show error toast
    }
  };

  return (
    <QuestionContext.Provider
      value={{
        ...state, // Spread state values into the provider
        fetchMcqQuestions,
        fetchCodingQuestions,
        registerMcqQuestion,
        registerCodingQuestion,
        updateMcqQuestion,
        updateCodingQuestion,
        deleteMcqQuestion,
        deleteCodingQuestion,
        searchQuestions,
      }}
    >
      {children}
    </QuestionContext.Provider>
  );
};

// Hook to use the QuestionContext
const useQuestionContext = () => {
  return useContext(QuestionContext);
};

export { useQuestionContext, QuestionProvider }; // Export the context and provider