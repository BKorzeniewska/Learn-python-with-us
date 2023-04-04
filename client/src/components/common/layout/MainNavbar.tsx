import { Container, Nav, Navbar } from "react-bootstrap";
import './nav-bar.css';
import 'bootstrap/dist/css/bootstrap.css';
import { useContext } from "react";
import { ThemeContext } from "../../themes/ThemeProvider";

export const MainNavbar = () => {
  const { theme } = useContext(ThemeContext);

  return (
    <Navbar data-theme={theme}>
      <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,1,0" />
      <Container>
        <Navbar.Brand href="#home">
          <img width="50" height="50" className="d-inline-block align-center mx-2" src={require("../../../assets/logo512.png")} />
          Learn Python with us
        </Navbar.Brand>
        <Nav className="me-auto">
          <Nav.Link href="#features"><span className="material-symbols-outlined">person</span></Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
} 