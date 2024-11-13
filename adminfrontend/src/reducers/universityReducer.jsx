const reducer = (state, action) => {
    switch (action.type) {
      case "FETCH_UNIVERSITIES":
        return { ...state, isLoading: true, isError: false };
      case "FETCH_UNIVERSITIES_SUCCESS":
        return {
          ...state,
          universities: action.payload,
          filteredUniversities: action.payload,
          isLoading: false
        };
      case "FETCH_UNIVERSITIES_FAILURE":
        return { ...state, isLoading: false, isError: true };
      case "REGISTER_UNIVERSITY":
        return {
          ...state,
          universities: [...state.universities, action.payload],
          filteredUniversities: [...state.filteredUniversities, action.payload],
        };
      case "UPDATE_UNIVERSITY":
        return {
          ...state,
          universities: state.universities.map((uni) =>
            uni.universityId === action.payload.universityId ? action.payload : uni
          ),
          filteredUniversities: state.filteredUniversities.map((uni) =>
            uni.universityId === action.payload.universityId ? action.payload : uni
          ),
        };
      case "DELETE_UNIVERSITY":
        return {
          ...state,
          universities: state.universities.filter((uni) => uni.universityId !== action.payload),
          filteredUniversities: state.filteredUniversities.filter((uni) => uni.universityId !== action.payload),
        };
      case "SEARCH_UNIVERSITIES":
        return {
          ...state,
          filteredUniversities: state.universities.filter((uni) =>
            uni.universityName.toLowerCase().includes(action.payload.toLowerCase())
          ),
        };
      default:
        return state;
    }
  };
  
  export default reducer;  