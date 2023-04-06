import React, { useContext } from 'react';
import './App.css';
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import HomeScreen from './components/home/HomeScreen';
import { ThemeContext } from './components/themes/ThemeProvider';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomeScreen />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
