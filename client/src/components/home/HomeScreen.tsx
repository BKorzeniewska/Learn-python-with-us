import React from 'react';
import { Container, Navbar, Nav, Button } from 'react-bootstrap';

type Props = {
    
}

const HomeScreen = (props : Props) =>{

    return (
        <>
          <Navbar bg="light" variant="light">
            <Container>
              <Navbar.Brand href="#home">React Bootstrap TypeScript</Navbar.Brand>
              <Nav className="me-auto">
                <Nav.Link href="#features">Features</Nav.Link>
                <Nav.Link href="#pricing">Pricing</Nav.Link>
              </Nav>
            </Container>
          </Navbar>
    
          <Container className="mt-3">
              
          </Container>
        </>
      );
}
export default HomeScreen