// Reducer function to handle state changes for questions (MCQ and Coding).
const questionReducer = (state, action) => {
  switch (action.type) {
      // Handle fetching questions (MCQ or Coding) - Set loading state
      case "FETCH_MCQ_QUESTIONS":
      case "FETCH_CODING_QUESTIONS":
          return { ...state, isLoading: true, isError: false };

      // Handle successful fetch of MCQ questions
      case "FETCH_MCQ_QUESTIONS_SUCCESS":
          return {
              ...state,
              isLoading: false,
              questions: [
                  ...state.questions,
                  ...action.payload.map((q) => { 
                      return { ...q, type: "MCQ" }; // Add type "MCQ" to each question
                  }),
              ],
              filteredquestions: [
                  ...state.filteredquestions,
                  ...action.payload.map((q) => { 
                      return { ...q, type: "MCQ" }; // Also update filtered questions
                  }),
              ],
          };

      // Handle successful fetch of Coding questions
      case "FETCH_CODING_QUESTIONS_SUCCESS":
          return {
              ...state,
              isLoading: false,
              questions: [
                  ...state.questions,
                  ...action.payload.map((q) => { 
                      return { ...q, type: "Coding" }; // Add type "Coding" to each question
                  }),
              ],
              filteredquestions: [
                  ...state.filteredquestions,
                  ...action.payload.map((q) => { 
                      return { ...q, type: "Coding" }; // Also update filtered questions
                  }),
              ],
          };

      // Handle failed fetch for MCQ or Coding questions
      case "FETCH_MCQ_QUESTIONS_FAILURE":
      case "FETCH_CODING_QUESTIONS_FAILURE":
          return { ...state, isLoading: false, isError: true };

      // Register a new MCQ question
      case "REGISTER_MCQ_QUESTION":
          return {
              ...state,
              questions: [...state.questions, { ...action.payload, type: "MCQ" }],
          };

      // Register a new Coding question
      case "REGISTER_CODING_QUESTION":
          return {
              ...state,
              questions: [...state.questions, { ...action.payload, type: "Coding" }],
          };

      // Update an existing MCQ question
      case "UPDATE_MCQ_QUESTION":
          return {
              ...state,
              questions: state.questions.map((q) =>
                  q.id === action.payload.id 
                      ? { ...action.payload, type: "MCQ" } 
                      : q
              ),
              filteredquestions: state.filteredquestions.map((q) =>
                  q.id === action.payload.id 
                      ? { ...action.payload, type: "MCQ" } 
                      : q
              ),
          };

      // Update an existing Coding question
      case "UPDATE_CODING_QUESTION":
          return {
              ...state,
              questions: state.questions.map((q) =>
                  q.id === action.payload.id 
                      ? { ...action.payload, type: "Coding" } 
                      : q
              ),
              filteredquestions: state.filteredquestions.map((q) =>
                  q.id === action.payload.id 
                      ? { ...action.payload, type: "Coding" } 
                      : q
              ),
          };

      // Delete an MCQ question
      case "DELETE_MCQ_QUESTION":
          return {
              ...state,
              questions: state.questions.filter(
                  (q) => q.id !== action.payload && q.type !== "MCQ"
              ),
              filteredquestions: state.filteredquestions.filter(
                  (q) => q.id !== action.payload && q.type !== "MCQ"
              ),
          };

      // Delete a Coding question
      case "DELETE_CODING_QUESTION":
          return {
              ...state,
              questions: state.questions.filter(
                  (q) => q.id !== action.payload && q.type !== "Coding"
              ),
              filteredquestions: state.filteredquestions.filter(
                  (q) => q.id !== action.payload && q.type !== "Coding"
              ),
          };

      // Filter questions based on search criteria
      case "SEARCH_QUESTIONS":
          return {
              ...state,
              filteredquestions: state.questions.filter((q) => {
                  if (action.payload === "") return true; // If no input, return all questions

                  // Match the search term with type, difficulty level, or category
                  return (
                      q.type.toLowerCase().includes(action.payload.toLowerCase()) || 
                      q.difficultyLevel?.toLowerCase().includes(action.payload.toLowerCase()) ||
                      q.category?.toLowerCase().includes(action.payload.toLowerCase())
                  );
              }),
          };

      // Return the current state by default
      default:
          return state;
  }
};

export default questionReducer;