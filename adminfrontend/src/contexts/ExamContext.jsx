import { createContext, useContext, useReducer } from "react";
import examService from "../services/examService"; 
import { fireToast } from "../components/fireToast"; 
import { useAuth } from "../contexts/AuthContext"; 
import reducer from "../reducers/examReducer";
import AuthHeader from "../Helper/AuthHeader";

// Initial state for the ExamContext
const initialState = {
  isLoading: false,
  isError: false,
  exams: [],
  filteredExams: [],
  searchTerm: "",
};

// Create a context for exams
const ExamContext = createContext();

// Provider component for the ExamContext
const ExamProvider = ({ children }) => {

  // Use the useReducer hook to manage state
  const [state, dispatch] = useReducer(reducer, initialState);
  const { logout } = useAuth();
  const { getExams, createExam, updateExam, deleteExam } = examService;
  const headers = AuthHeader();

  // Function to fetch all exams
  const fetchExams = async (navigate) => {
    dispatch({ type: "SET_LOADING" });
    try {
      const data = await getExams(headers);
      dispatch({ type: "FETCH_EXAMS_SUCCESS", payload: data });
    } catch (error) {
      handleAuthError(error, navigate);
      dispatch({ type: "SET_ERROR" });
    }
  };

  // Function to add a new exam
  const addExam = async (examData, navigate) => {
    try {
      examData.totalMarks = parseInt(examData.totalMarks);
    
      const newExam = await createExam(examData, headers);
      dispatch({ type: "ADD_EXAM", payload: newExam });
      fireToast("Exam added successfully", "success");
    } catch (error) {
      handleAuthError(error, navigate);
      fireToast("Failed to add exam", "error");
    }
  };

  // Function to edit an existing exam
  const editExam = async (id, inputData, navigate) => {
    try {
      
      console.log(inputData);
      
      const updatedData = {
        status: inputData.status,
        enable: inputData.enable,
        examName: inputData.examName,
        duration: inputData.duration,
        universities: inputData.universities,
        passingMarks: inputData.passingMarks,
        totalMarks: inputData.totalMarks,
        difficultyLevel: inputData.difficultyLevel,
        scheduleDate: inputData.scheduleDate,
      };
      

      const updatedExam = await updateExam(id, updatedData, headers);
      dispatch({ type: "UPDATE_EXAM", payload: updatedExam });
      fireToast("Exam updated successfully", "success");
    } catch (error) {
      handleAuthError(error, navigate);
      fireToast("Failed to update exam", "error");
    }
  };

  // Function to remove an exam
  const removeExam = async (id, navigate) => {
    try {
      await deleteExam(id, headers);
      dispatch({ type: "DELETE_EXAM", payload: id });
      fireToast("Exam deleted successfully", "success");
    } catch (error) {
      handleAuthError(error, navigate);
      fireToast("Failed to delete exam", "error");
    }
  };

  // Function to search exams
  const searchExams = (searchTerm) => {
    dispatch({ type: "SEARCH_EXAMS", payload: searchTerm });
  };

  // Function to handle authentication errors
  const handleAuthError = (error, navigate) => {
    if (error?.response?.status === 401 || error?.response?.status === 403) {
      logout();
      navigate("/");
      fireToast("Unauthorized access", "error");
    }
  };

  return (
    <ExamContext.Provider
      value={{
        ...state,
        fetchExams,
        addExam,
        editExam,
        removeExam,
        searchExams,
      }}
    >
      {children}
    </ExamContext.Provider>
  );
};

// Hook to access the ExamContext
const useExamContext = () => {
    return useContext(ExamContext);
};

export { ExamProvider, useExamContext };