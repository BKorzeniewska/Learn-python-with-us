import { ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../../home/AppWrapper';
import { UsersList } from './apis/users';
import { useError } from '../../home/ErrorContext';



export const AdminUsersScreen = () => {
    const navigate = useNavigate();
    const { articleId } = useParams();
    const [users, setUsers] = useState<UsersList>();
    const [searchInput, setSearchInput] = useState<string>();
    const { errorMessages, setError } = useError();


    const handleSearch = () => {
        console.log(searchInput);
    }


    return (
        <AppWrapper hideSidebar>
          <Container className="my-5">
          <h2>Wyszukaj użytkowników</h2>
          <Form className="d-flex mb-5 mt-5">
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2 rounded-pill"
              aria-label="Search"
              onChange={(e) => setSearchInput(e.target.value)}
            />
            <Button className="rounded-pill" variant="primary" onClick={handleSearch}>
              Search
            </Button>
          </Form>
            {/* {users &&
              users.map((user) => (
                <div
                  className="custom-list-item d-flex justify-content-between align-items-start"
                  onClick={() => {}}
                >
                  <div className="ms-2 me-auto">
                    <div className="fw-bold">{challenge.name}</div>
                    content placeholder
                  </div>
                </div>
              ))} */}
          </Container>
        </AppWrapper>
      );
      
};
