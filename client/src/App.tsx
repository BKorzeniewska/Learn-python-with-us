import './App.css';
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import HomeScreen from './components/home/HomeScreen';
import { LoginScreen } from './components/home/LoginScreen';
import { RegisterScreen } from './components/home/RegisterScreen';
import { AuthProvider } from './components/auth/AuthContext';
import { AcrticleScreen } from './components/home/ArticleScreen';
import { ErrorProvider } from './components/home/ErrorContext';
import { UserScreen } from './components/user/UserScreen';


function App() {
  return (
    <AuthProvider>
      <ErrorProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<HomeScreen />}></Route>
            <Route path="/login" element={<LoginScreen />}></Route>
            <Route path="/register" element={<RegisterScreen />}></Route>
            <Route path="/article/:articleId" element={<AcrticleScreen />}></Route>
            <Route path="/user/:userId" element={<UserScreen />}></Route>
          </Routes>
        </BrowserRouter>
      </ErrorProvider>
    </AuthProvider>
  );
}

export default App;
