import axios from 'axios';
import React from 'react';


const setTokenCallback = (token: string | null) => {
  delete axios.defaults.headers.common['Authorization'];

  axios.defaults.headers.common['Authorization'] = token;
  
  if (token !== null) {
    sessionStorage.setItem('token', token);
  } else {
    sessionStorage.removeItem('token');
  }
}
const isLoggedInCallback = (): boolean => {
  return sessionStorage.getItem('token') !== null;
};

export const AuthContext = React.createContext<{
  isLoggedIn: () => boolean
  setToken: (token: string | null) => void
  
}>({
  setToken: setTokenCallback,
  isLoggedIn: isLoggedInCallback,
});


// create AuthContext Provider
export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  
  if(sessionStorage.getItem('token')) {
    console.log('update')
    axios.defaults.headers.common['Authorization'] = sessionStorage.getItem('token');
  }

  return (
    <AuthContext.Provider value={{ setToken: setTokenCallback, isLoggedIn: isLoggedInCallback }}>
      {children}
    </AuthContext.Provider>
  );
};
