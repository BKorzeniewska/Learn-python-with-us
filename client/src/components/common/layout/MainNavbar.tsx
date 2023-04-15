import { Container, Nav, Navbar } from "react-bootstrap";
import './nav-bar.css'
import 'bootstrap/dist/css/bootstrap.css';
import { useContext } from "react";
import { ThemeContext } from "../../themes/ThemeProvider";


type Props ={
  toggleSidebar: () => void;
}

export const MainNavbar = (props : Props) => {
  const { theme } = useContext(ThemeContext);

  return (
    <Navbar data-theme={theme}>
      <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,1,0" />
      <Container>
      <Nav className="ml-auto">
          <Nav.Link onClick={() => {props.toggleSidebar()}}><span className="material-symbols-outlined">menu</span></Nav.Link>
        </Nav>
        <Navbar.Brand href="#home">
          <img width="50" height="50" className="d-inline-block align-center mx-2" src={require("../../../assets/logo512.png")} />
          <span className="d-none d-sm-inline-block">
          Learn Python with us
          </span>
        </Navbar.Brand>
        <Nav className="ml-auto">
          <Nav.Link href="#features"><span className="material-symbols-outlined">person</span></Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
} 