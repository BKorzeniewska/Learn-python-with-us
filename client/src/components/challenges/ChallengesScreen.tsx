import { ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../home/AppWrapper';
import { ChallengeResponse, getChallengesByArticleId } from './apis/challenge';
import { useError } from '../home/ErrorContext';



export const ChallengesScreen = () => {
    const navigate = useNavigate();
    const { articleId } = useParams();
    const [challenges, setChallenges] = useState<ChallengeResponse[]>();
    const { errorMessages, setError } = useError();

    useEffect(() => {
        if (articleId !== undefined) {
            getChallengesByArticleId(parseInt(articleId)).then((cha) => {
                if (cha.isOk) {
                    setChallenges(cha.value);
                } else {
                    setError("Nie udało się wczytać listy zadań");
                }
            });
        }
    }, [articleId]);


    return (
        <AppWrapper hideSidebar>
          <Container className="my-5">
            {challenges &&
              challenges.map((challenge) => (
                <div
                  className="custom-list-item d-flex justify-content-between align-items-start"
                  onClick={() => navigate(`/challenge`, { state: { challenge } })}
                >
                  <div className="ms-2 me-auto">
                    <div className="fw-bold">{challenge.name}</div>
                    content placeholder
                  </div>
                </div>
              ))}
          </Container>
        </AppWrapper>
      );
      
};
