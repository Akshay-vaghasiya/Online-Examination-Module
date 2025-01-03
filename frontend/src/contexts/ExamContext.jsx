import { createContext, useContext, useReducer } from "react";
import examService from "../services/examService"; 
import { fireToast } from "../components/fireToast"; 
import { useAuth } from "../contexts/AuthContext"; 
import reducer from "../reducers/examReducer";
import AuthHeader from "../helper/AuthHeader";
import QuestionService from "../services/questionService";

let initialState = {
  isLoading: false,
  isError: false,
  exams: [],
  startedExam: null,
  mcqQuestions: [],
  codingQuestions: [],
};

const ExamContext = createContext();

const ExamProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { logout } = useAuth();
  const { getExams, startExam, submitExam, autoSave } = examService;
  const { getMcqQuestions, getCodingQuestions } = QuestionService;
  const headers = AuthHeader();

  const fetchExams = async (navigate) => {
    
    dispatch({ type: "SET_LOADING" });
    try {
      const data = await getExams(headers);
      dispatch({ type: "FETCH_EXAMS_SUCCESS", payload: data });
    } catch (error) {
      handleAuthError(error, navigate);
      dispatch({ type: "SET_ERROR" });
    }
  }

  const startedExam1 = async (examid, navigate) => {
    try {
        const exam = await getExams(headers);
        const examData = exam.find((exam) => exam.id === parseInt(examid));
        const examdata = { ...examData, examid: examid };
        let data = await startExam(examid, headers);
        data = {...examdata, ...data};   
        console.log(data);
        
        dispatch({ type: "START_EXAM_SUCCESS", payload: data });
    } catch (error) {
        handleAuthError(error, navigate);
        fireToast("An error occurred " + error.message, "error");
    }
  }

  const getMcqQuestions1 = async (examid, navigate, pageno, first) => {
    try {
      const data = await getMcqQuestions(examid, headers, pageno);
      dispatch({ type: "FETCH_EXAMS_MCQ", payload: {data, first} });
    } catch (error) {
      handleAuthError(error, navigate);
      fireToast("An error occurred " + error.message, "error");
    }
  }

  const getCodingQuestions1 = async (examid, navigate, pageno, first) => {
    try {
      const data = await getCodingQuestions(examid, headers, pageno);
      dispatch({ type: "FETCH_EXAMS_CODING", payload: {data, first} });
    } catch (error) {
      handleAuthError(error, navigate);
      fireToast("An error occurred " + error.message, "error");
    }
  }

  const autosave = async (data, examid, navigate) => {
    try {
      await autoSave(data, examid, headers);   
    } catch (error) {
      handleAuthError(error, navigate);
      fireToast("An error occurred " + error.message, "error");
    }
  }

  const submitExam1 = async (studentexamid, navigate) => {
    try {
      await submitExam(studentexamid, headers);  
    } catch (error) {
      handleAuthError(error, navigate);
      fireToast("An error occurred " + error.message, "error");
    }
  }

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
        startedExam1,
        getMcqQuestions1,
        getCodingQuestions1,
        autosave,
        submitExam1
      }}
    >
      {children}
    </ExamContext.Provider>
  );
};

const useExamContext = () => {
    return useContext(ExamContext);
};

export { ExamProvider, useExamContext };