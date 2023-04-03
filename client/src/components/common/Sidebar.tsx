import React from 'react';
import { Navbar, Nav, NavDropdown, Dropdown } from 'react-bootstrap';

const Sidebar: React.FC = () => {
  return (
    <div className="bg-dark h-100 d-flex flex-column">
      <Navbar expand="lg" variant="dark">
        {/* <Navbar.Brand href="#home">My App</Navbar.Brand> */}
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="flex-column">
            <Nav.Link href="#home">Home</Nav.Link>
            <Nav.Link href="#about">About</Nav.Link>
            <Nav.Link href="#contact">Contact</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </div>
  );
};

export default Sidebar;