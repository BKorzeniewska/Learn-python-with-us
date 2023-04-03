import React from 'react';
import { Container, Navbar, Nav, Button } from 'react-bootstrap';

type Props = {
    
}

const HomeScreen = (props : Props) =>{

    return (
        <>
          <Navbar bg="dark" variant="dark">
            <Container>
              <Navbar.Brand href="#home">React Bootstrap TypeScript</Navbar.Brand>
              <Nav className="me-auto">
                <Nav.Link href="#features">Features</Nav.Link>
                <Nav.Link href="#pricing">Pricing</Nav.Link>
              </Nav>
            </Container>
          </Navbar>
        </>
      );
}
export default HomeScreen