import { ChangeEvent, ChangeEventHandler, ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../common/AppWrapper';
import { ChallengeResponse, getChallengesByArticleId, getChallengesByName } from './apis/challenge';
import { useError } from '../common/ErrorContext';
import { AuthContext } from '../auth/AuthContext';
import { FaPen, FaPlus, FaTrash } from 'react-icons/fa';
import { ChallengeListItem } from './ChallengeListItem';
import { LoadingSpinner } from '../common/Spinner';



export const ChallengesScreen = () => {
  const navigate = useNavigate();
  const { articleId } = useParams();
  const [challenges, setChallenges] = useState<ChallengeResponse[]>();
  const { errorMessages, setError } = useError();
  const [searchInput, setSearchInput] = useState<string>();
  const { isAuthorized } = useContext(AuthContext);
  const [isLoading, setisLoading] = useState(true);

  useEffect(() => {
    setisLoading(true);
    if (searchInput !== "" && searchInput !== undefined) {
      getChallengesByName(searchInput).then((cha) => {
        if (cha.isOk) {
          setChallenges(cha.value);
        } else {
          setError("Nie udało się wczytać listy zadań");
        }
      });
    }
    else if (articleId !== undefined) {
      getChallengesByArticleId(parseInt(articleId)).then((cha) => {
        if (cha.isOk) {
          setChallenges(cha.value);
        } else {
          setError("Nie udało się wczytać listy zadań");
        }
      });
    }
    setisLoading(false);
  }, [articleId, searchInput]);


  const handleKeyPress = (event: ChangeEvent<HTMLInputElement>) => {
    const val = event.target.value;
    setSearchInput(val);
    console.log(val);
  };

  const handleSearch = () => {
    console.log(searchInput);
  }


  return (
    <AppWrapper hideSidebar>
      <Container className="my-5">
        <Form className="d-flex mb-5">

          <Form.Control
            type="search"
            placeholder="Search"
            className="me-2 rounded-pill"
            aria-label="Search"
            onChange={handleKeyPress}
          />
          <Button className="rounded-pill" variant="primary" onClick={handleSearch}>
            Search
          </Button>
        </Form>
        <LoadingSpinner isLoading={isLoading}>
          {challenges &&
            challenges.map((challenge) => (
              <ChallengeListItem key={challenge.id} challenge={challenge} />
            ))}
        </LoadingSpinner>
        {isAuthorized("PRIVILEGED_USER") &&
          <Button className="rounded-pill" variant="primary" onClick={() => { navigate(`/admin/challenge/edit`) }}>
            Dodaj zadanie
          </Button>}
      </Container>
    </AppWrapper>
  );

};
