import { useContext, useState } from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import './side-bar.css'
import { ThemeContext } from '../../themes/ThemeProvider';

export const Sidebar = () => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleDropdownToggle = () => {
    setIsDropdownOpen(!isDropdownOpen);
  }

  const { theme } = useContext(ThemeContext);

  
  return (
    <Nav className="flex-column" data-theme={theme}>
      <Nav.Item>
        <Nav.Link className="sidebar-item" href="#">Link 1</Nav.Link>
      </Nav.Item>
      <Nav.Item>
        <Nav.Link className="sidebar-item" href="#">Link 2</Nav.Link>
      </Nav.Item>
      <Nav.Item>
        <Nav.Link onClick={handleDropdownToggle}>Dropdown</Nav.Link>
        {isDropdownOpen && (
          <>
            <Nav.Link className="sidebar-item" href="#">Action 1</Nav.Link>
            <Nav.Link className="sidebar-item" href="#">Action 2</Nav.Link>
            <Nav.Link className="sidebar-item" href="#">Separated link</Nav.Link>
          </>
        )}
      </Nav.Item>
      <Nav.Item>
        <Nav.Link className="sidebar-item" href="#">Link 3</Nav.Link>
      </Nav.Item>
    </Nav>
  );
};

