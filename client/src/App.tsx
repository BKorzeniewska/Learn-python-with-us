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
            <Route path="/" element={<Navigate to={home_path} />}></Route>
            <Route path="/home" element={<HomeScreen />}></Route>
            {/* <Route path="/seances/:date" element={<SeancesScreen />}></Route> */}
          </Routes>
        </BrowserRouter>
  );
}

export default App;
