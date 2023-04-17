import React, { useContext, useState } from 'react';
import './App.css';
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import HomeScreen from './components/home/HomeScreen';
import { ThemeContext } from './components/themes/ThemeProvider';
import { LoginScreen } from './components/home/LoginScreen';
import { RegisterScreen } from './components/home/RegisterScreen';
import { AuthContext, AuthProvider } from './components/auth/AuthContext';

function App() {
  return (
    <AuthProvider>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomeScreen />}></Route>
        <Route path="/login" element={<LoginScreen />}></Route>
        <Route path="/register" element={<RegisterScreen />}></Route>
      </Routes>
    </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
