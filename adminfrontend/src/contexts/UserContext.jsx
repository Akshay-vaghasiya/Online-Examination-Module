import { createContext, useContext } from "react";
import { useReducer } from "react";
import { fireToast } from "../components/fireToast";
import reducer from "../reducers/userReducer";
import { useAuth } from "./AuthContext";
import AuthHeader from "../Helper/AuthHeader";
import userService from "../services/userService";

// Create UserContext for managing user-related state and actions
const UserContext = createContext();

// Initial state for user-related data
const initialState = {
    isLoading: false,
    isError: false,
    users: [], // All users data
    filteredUsers: [], // Filtered users based on search
    searchTerm: "", // Search term for filtering users
    sortOption: "", // Sorting option for users
};

const UserProvider = ({ children }) => {
    // Set up reducer for managing state and actions
    const [state, dispatch] = useReducer(reducer, initialState);
    const { getAllUers, deleteuser, updateuser } = userService; // Destructure user service functions
    const headers = AuthHeader(); // Get authorization headers
    const { logout } = useAuth(); // Use authentication context

    // Fetch all users and handle unauthorized errors
    const getUsers = async (navigate) => {
        dispatch({ type: "SET_LOADING" });
        try {
            const data = await getAllUers(headers);
            dispatch({ type: "FETCH_USERS_SUCCESS", payload: data });
        } catch (error) {
            if (error.response.status === 401 || error.response.status === 403) {
                logout();
                navigate("/");  // Redirect to login on unauthorized error
            }
            dispatch({ type: "SET_ERROR" });
            fireToast("Failed to load users", "error");
        }
    };

    // Update a user and show appropriate messages
    const updateUser = async (updatedUser, navigate) => {
        const { userid, ...data } = updatedUser;
        try {
            const user = await updateuser(userid, data, headers);
            dispatch({ type: "UPDATE_USER", payload: user });
            fireToast("User updated successfully", "success");
        } catch (error) {
            if (error.response?.status === 401 || error.response?.status === 403) {
                logout();
                navigate("/");
            }
            fireToast("Failed to update user", "error");
        }
    };

    // Delete a user and handle unauthorized errors
    const deleteUser = async (userid, navigate) => {
        try {
            await deleteuser(userid, headers);
            dispatch({ type: "DELETE_USER", payload: userid });
            fireToast("User deleted successfully", "success");
        } catch (error) {
            if (error.response?.status === 401 || error.response?.status === 403) {
                logout();
                navigate("/");
            }
            fireToast("Failed to delete user", "error");
        }
    };

    // Dispatch search term for filtering users
    const searchUsers = (searchTerm) => {
        dispatch({ type: "SEARCH_USERS", payload: searchTerm });
    };

    // Dispatch sorting option for sorting users
    const sortUsers = (sortOption) => {
        dispatch({ type: "SORT_USERS", payload: sortOption });
    };

    // Provide context values to components
    return (
        <>
            <UserContext.Provider value={{ ...state, getUsers, updateUser, deleteUser, searchUsers, sortUsers }}>
                {children}
            </UserContext.Provider>
        </>
    );
};

// Custom hook to use UserContext in components
const useUserContext = () => {
    return useContext(UserContext);
};

export { UserProvider, useUserContext };