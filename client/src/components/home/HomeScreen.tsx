import React, { useContext } from 'react';
import { Container, Navbar, Nav, Button } from 'react-bootstrap';
import { MainNavbar } from '../common/layout/MainNavbar';
import { ThemeContext } from '../themes/ThemeProvider';
import '../../App.css';

type Props = {
    
}

const HomeScreen = (props : Props) =>{
    const { theme, toggleTheme } = useContext(ThemeContext);

    return (
        <>
          <MainNavbar/>    
          <Container className="mt-3" data-theme={theme}>
            <Button
              onClick={() => {
                toggleTheme();
              }}
            >
              {theme === 'light' ? 'Dark' : 'Light'} Theme
            </Button>

            <Button
              onClick={() => {
                toggleTheme();
              }}
              variant="secondary"
            >
              Secondary button
            </Button>
          </Container>
        </>
      );
}
export default HomeScreen