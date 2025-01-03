const examReducer = (state, action) => {
    switch (action.type) {
      case "SET_LOADING":
        return { ...state, isLoading: true, isError: false };
      case "SET_ERROR":
        return { ...state, isLoading: false, isError: true };
      case "FETCH_EXAMS_SUCCESS":
        return {
          ...state,
          isLoading: false,
          exams: action.payload,
        };
      case "START_EXAM_SUCCESS":
        return {
          ...state,
          startedExam: action.payload,  
        };
      case "FETCH_EXAMS_MCQ":
        return {
          ...state,
          mcqQuestions: action.payload?.data?.map((q, index) => {
            return { ...q, idno: action.payload.first + index };
          }),
        };
      case "FETCH_EXAMS_CODING":
        return {
          ...state,
          codingQuestions: action.payload?.data?.map((q, index) => {
            return { ...q, idno: action.payload.first + index };
          })
        };
      default:
        return state;
    }
};
  
export default examReducer;