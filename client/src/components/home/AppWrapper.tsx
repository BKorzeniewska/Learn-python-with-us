import React, { useContext, useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { ReactNode } from 'react';
import { ThemeContext } from '../themes/ThemeProvider';
import { MainNavbar } from '../common/layout/MainNavbar';
import { Sidebar } from '../common/layout/Sidebar';

export type AppProps = {
  children: ReactNode;
  hideSidebar?: boolean;
}

export const AppWrapper = (props: AppProps) => {

  const SIDEBAR_WIDTH: number = 300;

  const { theme } = useContext(ThemeContext);
  const [hideSidebar, setHideSidebar] = useState(props.hideSidebar === undefined || props.hideSidebar == true ? true : false);
  const [sidebarWidth, setSidebarWidth] = useState(hideSidebar ? 0 : SIDEBAR_WIDTH); // Set the initial width of the sidebar

  const toggleSidebar = () => {
    setHideSidebar(!hideSidebar);
    setSidebarWidth(hideSidebar ? SIDEBAR_WIDTH : 0); // Set the width of the sidebar based on the hideSidebar state
  };

  return (
    <>
      <MainNavbar toggleSidebar={toggleSidebar} />
      <Container fluid>
        <Row noGutters>
          <Col xs={12} md="auto" className="p-0">
            <div id="side">
              <Sidebar width={sidebarWidth} >
                
              </Sidebar>
            </div>
          </Col>
          <Col xs={12} md className="p-0 d-flex">
            <div id="App" data-theme={theme} className="flex-grow-1">
              <div className="content">
                {props.children}
              </div>
            </div>
          </Col>
        </Row>
      </Container>
    </>
  );
};
