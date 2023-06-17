import React from 'react';
import { Container, Button, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { AppWrapper } from '../common/AppWrapper';
import { useError } from '../common/ErrorContext';
import { FaNewspaper, FaUserCog } from 'react-icons/fa';

export const AdminScreen = () => {
  const { setError } = useError();

  return (
    <AppWrapper hideSidebar>
      <Container className="my-5">
        <Row>
          <Col>
            <h2>Panel administratora</h2>
          </Col>
        </Row>
        <Row className="mt-4">
          <Col>
            <Link to="/admin/articles" className="admin-link">
              <Button variant="primary" size="lg">
                <FaNewspaper className="mr-2" />
                Artykuły
              </Button>
            </Link>
          </Col>
        </Row>
        <Row className="mt-4">
          <Col>
            <Link to="/admin/users" className="admin-link">
              <Button variant="primary" size="lg">
                <FaUserCog className="mr-2" />
                Użytkownicy
              </Button>
            </Link>
          </Col>
        </Row>
      </Container>
    </AppWrapper>
  );
};
