import axios from 'axios';
import React from 'react';


const setTokenCallback = (token: string | null) => {
  delete axios.defaults.headers.common['Authorization'];

  axios.defaults.headers.common['Authorization'] = token;
  
  if (token) {
    sessionStorage.setItem('token', token);
  } else {
    sessionStorage.removeItem('token');
  }
}

export const AuthContext = React.createContext<{
  setToken: (token: string | null) => void
}>({
  setToken: setTokenCallback,
});


// create AuthContext Provider
export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  
  if(sessionStorage.getItem('token')) {
    console.log('update')
    axios.defaults.headers.common['Authorization'] = sessionStorage.getItem('token');
  }

  return (
    <AuthContext.Provider value={{ setToken: setTokenCallback }}>
      {children}
    </AuthContext.Provider>
  );
};
