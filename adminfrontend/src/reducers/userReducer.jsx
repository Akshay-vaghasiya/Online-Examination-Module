// Reducer function to manage user-related state based on action types
const reducer = (state, action) => {
    switch (action.type) {
        // Set loading state to true and reset error state
        case "SET_LOADING":
            return { ...state, isLoading: true, isError: false };

        // Set error state to true and stop loading
        case "SET_ERROR":
            return { ...state, isLoading: false, isError: true };

        // Store fetched users in state, set loading to false, and reset filtered users
        case "FETCH_USERS_SUCCESS":
            return { ...state, isLoading: false, users: action.payload, filteredUsers: action.payload };

        // Filter users based on search term (username, email, university, or role)
        case "SEARCH_USERS":
            return {
                ...state,
                searchTerm: action.payload,
                filteredUsers: state.users.filter((user) =>
                    user.username.toLowerCase().includes(action.payload.toLowerCase()) ||
                    user.email.toLowerCase().includes(action.payload.toLowerCase()) ||
                    user.university.toLowerCase().includes(action.payload.toLowerCase()) ||
                    user.role.toLowerCase().includes(action.payload.toLowerCase())
                ),
            };

        // Sort users based on specified sort option (username, email, or university)
        case "SORT_USERS":
            const sortedUsers = [...state.filteredUsers].sort((a, b) => {
                if (action.payload === "username") return a.username.localeCompare(b.username);
                if (action.payload === "email") return a.email.localeCompare(b.email);
                if (action.payload === "university") return a.university.localeCompare(b.university);
                return 0;
            });
            return {
                ...state,
                sortOption: action.payload,
                filteredUsers: sortedUsers,
            };

        // Update a specific user in both users and filteredUsers arrays
        case "UPDATE_USER":
            return {
                ...state,
                users: state.users.map(user =>
                    user.userid === action.payload.userid ? action.payload : user
                ),
                filteredUsers: state.filteredUsers.map(user =>
                    user.userid === action.payload.userid ? action.payload : user
                ),
            };

        // Delete a user by removing it from both users and filteredUsers arrays
        case "DELETE_USER":
            return {
                ...state,
                users: state.users.filter(user => user.userid !== action.payload),
                filteredUsers: state.filteredUsers.filter(user => user.userid !== action.payload),
            };

        // Default case to return the current state if action type is unrecognized
        default:
            return state;
    }
};

export default reducer;
