// AuthHeader function to create authorization headers for API requests
const AuthHeader = () => {
    
    // Retrieve JWT token from local storage
    const token = localStorage.getItem('token');

    // Define headers with default content type and authorization if token exists
    const headers = {
      'Content-Type': 'application/json', 
      'Authorization': `Bearer ${token}`, // Attach token for secure endpoints
      'Access-Control-Allow-Origin': '*'  // Allow cross-origin requests
    };
    
    // Return headers if token exists; otherwise, return an empty object
    return token ? headers : {};
}

export default AuthHeader;
