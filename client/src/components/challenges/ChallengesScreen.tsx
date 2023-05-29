import { ChangeEvent, ChangeEventHandler, ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../home/AppWrapper';
import { ChallengeResponse, getChallengesByArticleId, getChallengesByName } from './apis/challenge';
import { useError } from '../home/ErrorContext';



export const ChallengesScreen = () => {
  const navigate = useNavigate();
  const { articleId } = useParams();
  const [challenges, setChallenges] = useState<ChallengeResponse[]>();
  const { errorMessages, setError } = useError();
  const [searchInput, setSearchInput] = useState<string>();

  useEffect(() => {
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
        {challenges &&
          challenges.map((challenge) => (
            <div
              className="custom-list-item d-flex justify-content-between align-items-start"
              onClick={() => navigate(`/challenge`, { state: { challenge } })}
            >
              <div className="ms-2 me-auto">
                <div className="fw-bold">{challenge.name}</div>
                {challenge.question.length > 150 ? (
                  <span>{challenge.question.slice(0, 120).trim()}...</span>
                ) : (
                  <span>{challenge.question}</span>
                )}
              </div>
            </div>
          ))}
      </Container>
    </AppWrapper>
  );

};
