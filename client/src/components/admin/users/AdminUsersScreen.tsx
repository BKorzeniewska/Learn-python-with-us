import { ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Pagination, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../../home/AppWrapper';
import { GetUsersRequest, GetUsersResponse, getUsers } from './apis/users';
import { useError } from '../../home/ErrorContext';



export const AdminUsersScreen = () => {
  const navigate = useNavigate();
  const { articleId } = useParams();
  const [users, setUsers] = useState<GetUsersResponse>();
  const [searchInput, setSearchInput] = useState<string>("");
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [sendRequest, setSendRequest] = useState<boolean>(false);
  const { errorMessages, setError } = useError();
  const location = useLocation();


  const handleSearch = () => {
    console.log(searchInput);
    setSendRequest(!sendRequest);
  }


  useEffect(() => {
    const req: GetUsersRequest = {
      pageNumber: pageNumber,
      query: searchInput,
    };
    getUsers(req).then((data) => {
      if (data.isOk) {
        setUsers(data.value);
      } else {
        setError("Nie udało się załadować listy użytkowników");
      }
    });

  }, [sendRequest, location, pageNumber,searchInput]);


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
        {users &&
          users.results.map((user) => (
            <div
              className="custom-list-item d-flex justify-content-between align-items-start"
              onClick={() => { }}
            >
              <div className="ms-2 me-auto">
                <div className="fw-bold">{user.nickname}</div>
                {user.email}, {user.firstname}, {user.lastname}
              </div>
            </div>
          ))}
        {users &&
          <Pagination className='d-flex justify-content-center mt-4'>
            <Pagination.First
              disabled={users?.intCurrentPage === 1}
              onClick={() => setPageNumber(1)}
            />

            <Pagination.Prev
              disabled={users?.intCurrentPage === 1}
              onClick={() => setPageNumber(users?.intCurrentPage - 1)}
            />

            {users &&
              Array.from({ length: users.totalPages }, (_, index) => {
                if (index + 1 <= users.intCurrentPage + 3 && index + 1 >= users.intCurrentPage - 3) {
                  return (
                    <Pagination.Item
                      key={index + 1}
                      active={index + 1 === users.intCurrentPage}
                      onClick={() => setPageNumber(index + 1)}
                    >
                      {index + 1}
                    </Pagination.Item>
                  );
                }
                return null;
              })}

            <Pagination.Next
              disabled={users?.intCurrentPage === users?.totalPages}
              onClick={() => setPageNumber(users?.intCurrentPage + 1)}
            />


            <Pagination.Last
              disabled={users?.intCurrentPage === users?.totalPages}
              onClick={() => setPageNumber(users?.totalPages)}
            />

          </Pagination>}


      </Container>
    </AppWrapper>
  );

};
