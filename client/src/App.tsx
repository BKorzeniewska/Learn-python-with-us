import './App.css';
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import HomeScreen from './components/home/HomeScreen';
import { LoginScreen } from './components/home/LoginScreen';
import { RegisterScreen } from './components/home/RegisterScreen';
import { AuthProvider } from './components/auth/AuthContext';
import { AcrticleScreen } from './components/home/ArticleScreen';
import { ErrorProvider } from './components/home/ErrorContext';
import { UserScreen } from './components/user/UserScreen';
import { AdminArticlesScreen } from './components/admin/article/AdminArticlesScreen';
import { ArticleEditionScreen } from './components/admin/article/ArticleEditionScreen';
import { ChallengesScreen } from './components/challenges/ChallengesScreen';
import { ChallengeScreen } from './components/challenges/ChallengeScreen';
import { AdminUsersScreen } from './components/admin/users/AdminUsersScreen';


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
            <Route path="/user/:userId?" element={<UserScreen />}></Route>
            <Route path="/admin/articles" element={<AdminArticlesScreen />}></Route>
            <Route path="/admin/edit/:articleId?" element={<ArticleEditionScreen />}></Route>
            <Route path="/admin/users" element={<AdminUsersScreen />}></Route>
            <Route path="/challenges/:articleId" element={<ChallengesScreen />}></Route>
            <Route path="/challenge" element={<ChallengeScreen />}></Route>
          </Routes>
        </BrowserRouter>
      </ErrorProvider>
    </AuthProvider>
  );
}

export default App;
