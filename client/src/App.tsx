import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import HomeScreen from './components/home/HomeScreen';

function App() {

  const home_path: string = "/home";

  return (
    <BrowserRouter>
          <Routes>
            <Route path="/" element={<HomeScreen/>}></Route>
            
          </Routes>
        </BrowserRouter>
  );
}

export default App;
