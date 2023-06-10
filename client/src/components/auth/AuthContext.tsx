import axios from 'axios';
import React from 'react';
import { UserRole, roleRank } from '../admin/users/apis/users';


const setTokenCallback = (token: string | null) => {
  delete axios.defaults.headers.common['Authorization'];

  if (token !== null) {
    axios.defaults.headers.common['Authorization'] = "Bearer " + token;
    sessionStorage.setItem('token', "Bearer " + token);
  } else {
    sessionStorage.removeItem('token');
  }
  console.log(axios.defaults.headers.common['Authorization']);
}

const setRoleCallback = (role: UserRole | null) => {
  if (role !== null) {
    sessionStorage.setItem('role', role);
  } else {
    sessionStorage.removeItem('role');
  }
}

const getRoleCallback = (): UserRole | null => {
  return sessionStorage.getItem('role') as UserRole | null;
}

const isLoggedInCallback = (): boolean => {
  return sessionStorage.getItem('token') !== null;
};

const isAuthorizedCallback = (role: UserRole): boolean => {
  const myRole = getRoleCallback();
  if (myRole === null) {
    return false;
  }
  const myRoleRank = roleRank[myRole];
  const otherRoleRank = roleRank[role];
  return myRoleRank >= otherRoleRank;

}

export const AuthContext = React.createContext<{
  isLoggedIn: () => boolean
  setToken: (token: string | null) => void
  setRole: (role: UserRole | null) => void
  getRole: () => UserRole | null
  isAuthorized: (role: UserRole) => boolean

}>({
  setToken: setTokenCallback,
  isLoggedIn: isLoggedInCallback,
  setRole: setRoleCallback,
  getRole: getRoleCallback,
  isAuthorized: isAuthorizedCallback,
});


// create AuthContext Provider
export const AuthProvider = ({ children }: { children: React.ReactNode }) => {

  if (sessionStorage.getItem('token')) {
    console.log('update')
    axios.defaults.headers.common['Authorization'] = sessionStorage.getItem('token');
  }

  return (
    <AuthContext.Provider value={{
      setToken: setTokenCallback,
      isLoggedIn: isLoggedInCallback,
      setRole: setRoleCallback,
      getRole: getRoleCallback,
      isAuthorized: isAuthorizedCallback,
    }}>
      {children}
    </AuthContext.Provider>
  );
};
