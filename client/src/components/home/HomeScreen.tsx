import React, { useState } from 'react';
import { Container, Nav, Button, Row, Col } from 'react-bootstrap';
import Navbar from '../common/Navbar';
import Sidebar from '../common/Sidebar';


type Props = {

}

const HomeScreen = (props: Props) => {

  const [isSidebarCollapsed, setIsSidebarCollapsed] = useState(false);

  const handleSidebarToggle = () => {
    setIsSidebarCollapsed(!isSidebarCollapsed);
  };

  return (
    <div>
      <Navbar />
      <Container fluid className="d-flex vh-100">
      <Row>
        <Col md={4} className="p-0">
          <Sidebar />
        </Col>
        <Col md={8}>
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
        </Col>
      </Row>
    </Container>


    </div>
  );
}
export default HomeScreen