import { Container, Dropdown, Nav, Navbar, NavDropdown } from "react-bootstrap";
import './nav-bar.css'
import 'bootstrap/dist/css/bootstrap.css';
import { MouseEventHandler, useContext, useState } from "react";
import { ThemeContext } from "../../themes/ThemeProvider";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../auth/AuthContext";


type Props = {
  toggleSidebar: () => void;
}

export const MainNavbar = (props: Props) => {
  const { theme, toggleTheme } = useContext(ThemeContext);
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const { setToken, isLoggedIn, isAuthorized, setRole, setUser, getUser } = useContext(AuthContext);

  const logout = (event: React.MouseEvent<HTMLElement, MouseEvent>) => {
    setToken(null);
    setRole(null);
    setUser(null);
    event.stopPropagation();
    setIsDropdownOpen(!isDropdownOpen);
    navigate("/");
  }

  return (
    <Navbar data-theme={theme} sticky="top" expand="lg">
      <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,1,0" />
      <Container>
        <Nav className="ml-auto">
          <Nav.Link onClick={() => { props.toggleSidebar() }}><span className="material-symbols-outlined">menu</span></Nav.Link>
        </Nav>
        <Navbar.Brand onClick={() => { navigate("/") }}>
          <img width="50" height="50" className="d-inline-block align-center mx-2" src={require("../../../assets/logo512.png")} />
          <span className="d-none d-sm-inline-block">
            Learn Python with us
          </span>
        </Navbar.Brand>
        <Nav className="ml-auto text-center logged-in">
          {!isLoggedIn() ? <Nav.Link onClick={() => { navigate("/login") }}>Zaloguj</Nav.Link> :

          <NavDropdown title={<span className="">{getUser()?.nickname}</span>} id="dropdown-basic" drop="down-centered">
            <NavDropdown.Item onClick={() => { toggleTheme(); }}>
              {
                theme === "light" ? <span className="material-symbols-outlined">dark_mode</span> : <span className="material-symbols-outlined">light_mode</span>
              }

            </NavDropdown.Item>
            <NavDropdown.Item onClick={() => navigate("/user/")}>Profil</NavDropdown.Item>
            <NavDropdown.Item onClick={(event) => logout(event)}>Wyloguj</NavDropdown.Item>
            {isAuthorized("ADMIN") &&
            <>
            <Dropdown.Divider />
              <NavDropdown.Item onClick={() => navigate("/admin/")}>Panel administratora</NavDropdown.Item>
              </>
              }
          </NavDropdown>
}
        </Nav>
      </Container>
    </Navbar>
  );
} 