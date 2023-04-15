import { Col, Container, Row } from "react-bootstrap";
import { MainNavbar } from "../common/layout/MainNavbar";
import { ReactNode, useContext, useState } from "react";
import { ThemeContext } from "../themes/ThemeProvider";
import { Sidebar } from "../common/layout/Sidebar";


export type AppProps = {
  children: ReactNode;
  hideSidebar?: boolean;
}

export const AppWrapper = (props: AppProps) => {
  const { theme } = useContext(ThemeContext);
  const [hideSidebar, setHideSidebar] = useState(props.hideSidebar == true ? true : false);

  const toggleSidebar = () => {setHideSidebar(!hideSidebar)};

  return (
    <>
      <MainNavbar toggleSidebar={toggleSidebar}/>
      <Container fluid>
        <Row noGutters>
          {hideSidebar && <Col className="col-2 p-0 "><Sidebar /></Col>}
          <Col className="p-0">
            <div id="App" data-theme={theme}>
              {props.children}
            </div>
          </Col>
        </Row>
      </Container>
    </>);
}