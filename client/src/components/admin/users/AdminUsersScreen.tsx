import { useEffect, useState } from 'react';
import { Button, Container, Form, Modal, NavDropdown, Pagination} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../../common/AppWrapper';
import { ChangeRoleRequest, GetUsersRequest, GetUsersResponse, User, UserRole, changeRole, deleteUser, getUsers } from './apis/users';
import { useError } from '../../common/ErrorContext';
import { FaTrash } from 'react-icons/fa';
import "./user-list.css";



export const AdminUsersScreen = () => {
  const navigate = useNavigate();
  const { articleId } = useParams();
  const [users, setUsers] = useState<GetUsersResponse>();
  const [searchInput, setSearchInput] = useState<string>("");
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [sendRequest, setSendRequest] = useState<boolean>(false);
  const { errorMessages, setError } = useError();
  const location = useLocation();
  const [userToDelete, setUserToDelete] = useState<User>();
  const [showModal, setShowModal] = useState(false);


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

  }, [sendRequest, location, pageNumber, searchInput]);


  const handleRoleChange = (user: User, newRole: UserRole) => {
    const updatedUser = { ...user, role: newRole };
    const req: ChangeRoleRequest = {
      userId: user.id,
      role: newRole,
    }
    changeRole(req).then((response) => {
      if (response.isOk) {
        // Update the user's role in the state
        setUsers((prevUsers) => {
          if (prevUsers) {
            const updatedResults = prevUsers.results.map((u) => {
              if (u.nickname === user.nickname) {
                return updatedUser;
              }
              return u;
            });
            return { ...prevUsers, results: updatedResults };
          }
          return prevUsers;
        });
      } else {
        setError("Failed to update user role.\n" + response.error.errorMessage + "\n" + response.error.errorCode);
      }
    });
  }

  function handleDeleteUser(): void {
    deleteUser(userToDelete?.email!).then((response) => {
      if (response.isOk) {
        // Update the user's role in the state
        setUsers((prevUsers) => {
          if (prevUsers) {
            const updatedResults = prevUsers.results.filter((u) => u.email !== userToDelete?.email);
            return { ...prevUsers, results: updatedResults };
          }
          return prevUsers;
        });
      } else {
        setError("Failed to delete user.\n" + response.error.errorMessage + "\n" + response.error.errorCode);
      }
    }
    );
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
              <div className='option-section'>
                <NavDropdown
                  title={user.role}
                  id={`dropdown-${user.nickname}`}
                  onSelect={(selectedRole) => handleRoleChange(user, selectedRole as UserRole)}
                >
                  <NavDropdown.Item eventKey="USER">USER</NavDropdown.Item>
                  <NavDropdown.Item eventKey="ADMIN">ADMIN</NavDropdown.Item>
                  <NavDropdown.Item eventKey="MODERATOR">MODERATOR</NavDropdown.Item>
                  <NavDropdown.Item eventKey="PRIVILEGED_USER">PRIVILEGED USER</NavDropdown.Item>
                </NavDropdown>
                <span className="modify-button">
                  <FaTrash onClick={(event) => { event.preventDefault(); event.stopPropagation(); setUserToDelete(user); setShowModal(true) }} />
                </span>
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

          <Modal show={showModal} onHide={() => {setShowModal(false)}}>
                <Modal.Header closeButton>
                    <Modal.Title>Usunąć Użytkownika?</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Czy jesteś pewny że chcesz usunąć użytkownika  {userToDelete?.nickname} od tego momentu nie będzie się mógł zalogować?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={(event) => { event.preventDefault();event.stopPropagation();setShowModal(false) }}>
                        Nie
                    </Button>
                    <Button variant="danger" onClick={() => {handleDeleteUser(); setShowModal(false)}}>
                        Tak
                    </Button>
                </Modal.Footer>
            </Modal>


      </Container>
    </AppWrapper>
  );

};
