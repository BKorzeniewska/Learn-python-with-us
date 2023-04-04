import { Container, Nav, Navbar } from "react-bootstrap";
import './nav-bar.css';
import 'bootstrap/dist/css/bootstrap.css';

export const MainNavbar = () => { 
    return (
        <Navbar id="dark-nav-bar" >
            <Container>
              <Navbar.Brand href="#home">
                <img width="50" height="50" className="d-inline-block align-center mx-2" src={require("../../../assets/logo512.png")}/>
                Learn Python with us  
              </Navbar.Brand>
              <Nav className="me-auto">
                <Nav.Link href="#features">Features</Nav.Link>
              </Nav>
            </Container>
          </Navbar>
    );
} 