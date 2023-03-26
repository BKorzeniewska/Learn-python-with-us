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
            <h1>Welcome to React Bootstrap TypeScript</h1>
            <p>
              This is a sample homepage using React, Bootstrap, and TypeScript.
            </p>
            <p>
              To get started, edit <code>src/App.tsx</code> and save to reload.
            </p>
            <Button variant='primary'>Hello react</Button>
          </Container>
        </>
      );
}
export default HomeScreen