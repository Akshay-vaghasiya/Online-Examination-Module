const examReducer = (state, action) => {
    switch (action.type) {

      // Action to set loading state
      case "SET_LOADING":
        return { ...state, isLoading: true, isError: false };

      // Action to set error state
      case "SET_ERROR":
        return { ...state, isLoading: false, isError: true };

      // Action to fetch exams
      case "FETCH_EXAMS_SUCCESS":
        return {
          ...state,
          isLoading: false,
          exams: action.payload,
          filteredExams: action.payload,
        };

      // Action to add a new exam
      case "ADD_EXAM":
        return {
          ...state,
          exams: [...state.exams, action.payload],
          filteredExams: [...state.filteredExams, action.payload],
        };

      // Action to update an exam
      case "UPDATE_EXAM":
        return {
          ...state,
          exams: state.exams.map((exam) =>
            exam.id === action.payload.id ? action.payload : exam
          ),
          filteredExams: state.filteredExams.map((exam) =>
            exam.id === action.payload.id ? action.payload : exam
          ),
        };

      // Action to delete an exam
      case "DELETE_EXAM":
        return {
          ...state,
          exams: state.exams.filter((exam) => exam.id !== action.payload),
          filteredExams: state.filteredExams.filter(
            (exam) => exam.id !== action.payload
          ),
        };

      // Action to search exams
      case "SEARCH_EXAMS":
        return {
          ...state,
          searchTerm: action.payload,
          filteredExams: state.exams.filter((exam) =>
            exam.examName.toLowerCase().includes(action.payload.toLowerCase())
          ),
        };
      default:
        return state;
    }
};
  
export default examReducer;