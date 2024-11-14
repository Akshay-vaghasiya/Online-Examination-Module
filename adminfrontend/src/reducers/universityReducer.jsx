const reducer = (state, action) => {
  switch (action.type) {
    // Action to start fetching universities (sets loading state)
    case "FETCH_UNIVERSITIES":
      return { ...state, isLoading: true, isError: false };
    
    // Action for successful fetch of universities (updates universities list and stops loading)
    case "FETCH_UNIVERSITIES_SUCCESS":
      return {
        ...state,
        universities: action.payload,            
        filteredUniversities: action.payload,    
        isLoading: false                        
      };
    
    // Action for fetch failure (stops loading and sets error state)
    case "FETCH_UNIVERSITIES_FAILURE":
      return { ...state, isLoading: false, isError: true };
    
    // Action to register a new university (adds university to both lists)
    case "REGISTER_UNIVERSITY":
      return {
        ...state,
        universities: [...state.universities, action.payload],            
        filteredUniversities: [...state.filteredUniversities, action.payload], 
      };
    
    // Action to update university details (updates university in both lists by ID)
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
    
    // Action to delete a university (removes university from both lists by ID)
    case "DELETE_UNIVERSITY":
      return {
        ...state,
        universities: state.universities.filter((uni) => uni.universityId !== action.payload),
        filteredUniversities: state.filteredUniversities.filter((uni) => uni.universityId !== action.payload),
      };
    
    // Action to search universities (filters list based on search query)
    case "SEARCH_UNIVERSITIES":
      return {
        ...state,
        filteredUniversities: state.universities.filter((uni) =>
          uni.universityName.toLowerCase().includes(action.payload.toLowerCase())
        ),                                                               
      };
    
    // Default case to return current state if action type is unknown
    default:
      return state;
  }
};

export default reducer;
