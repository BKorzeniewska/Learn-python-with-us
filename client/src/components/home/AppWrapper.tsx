import { Col, Container, Row } from "react-bootstrap";
import { MainNavbar } from "../common/layout/MainNavbar";
import { ReactNode, useContext } from "react";
import { ThemeContext } from "../themes/ThemeProvider";
import { Sidebar } from "../common/layout/Sidebar";


export type AppProps = {
  children: ReactNode;
  hideSidebar?: boolean;
}

export const AppWrapper = (props: AppProps) => {
  const { theme } = useContext(ThemeContext);

  return (
    <>
      <MainNavbar />
      <Container fluid>
        <Row noGutters>
          <Col className="col-2 p-0">
            {!props.hideSidebar && <Sidebar />}
          </Col>
          <Col className="col-10 p-0">
            <div id="App" data-theme={theme}>
              {props.children}
            </div>
          </Col>
        </Row>
      </Container>
    </>);
}