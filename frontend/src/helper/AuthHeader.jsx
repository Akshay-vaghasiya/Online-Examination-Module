const AuthHeader = () => {
    
    const token = (localStorage.getItem('token'));
    const headers = {
      'Content-Type': 'application/json', 
      'Authorization': `Bearer ${token}`,
      'Access-Control-Allow-Origin' : '*'
    };
    
        if (token) {
            return headers;
        } else {
            return {};
        }
}

export default AuthHeader