import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import './nav-bar.css'
import 'bootstrap/dist/css/bootstrap.css';
import { MouseEventHandler, useContext, useState } from "react";
import { ThemeContext } from "../../themes/ThemeProvider";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../auth/AuthContext";


type Props ={
  toggleSidebar: () => void;
}

export const MainNavbar = (props : Props) => {
  const { theme } = useContext(ThemeContext);
  const navigate = useNavigate();
  const token = sessionStorage.getItem('token');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const { setToken, isLoggedIn } = useContext(AuthContext);

  const logout = (event: React.MouseEvent<HTMLElement, MouseEvent>) =>{
    setToken(null);
    event.stopPropagation();
    setIsDropdownOpen(!isDropdownOpen);
  }
  
  const personButtonCallback = () => {
    if(!isLoggedIn()){
      navigate("/login");
    }
    else{
      setIsDropdownOpen(!isDropdownOpen);
    }
  }

  return (
    <Navbar data-theme={theme}>
      <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,1,0" />
      <Container>
      <Nav className="ml-auto">
          <Nav.Link onClick={() => {props.toggleSidebar()}}><span className="material-symbols-outlined">menu</span></Nav.Link>
        </Nav>
        <Navbar.Brand onClick={() => {navigate("/")}}>
          <img width="50" height="50" className="d-inline-block align-center mx-2" src={require("../../../assets/logo512.png")} />
          <span className="d-none d-sm-inline-block">
          Learn Python with us
          </span>
        </Navbar.Brand>
        <Nav className="ml-auto">
            <NavDropdown title={<span className="material-symbols-outlined">person</span>} id="basic-nav-dropdown" onClick={() => personButtonCallback()} show={isDropdownOpen}>
              <NavDropdown.Item onClick = {() => navigate("/profile")}>Profil</NavDropdown.Item>
              <NavDropdown.Item onClick = {(event) => logout(event)}>Wyloguj</NavDropdown.Item>
            </NavDropdown>
        </Nav>
      </Container>
    </Navbar>
  );
} 