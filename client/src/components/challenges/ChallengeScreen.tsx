import { ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../home/AppWrapper';
import { ChallengeResponse, getChallengesByArticleId } from './apis/challenge';
import { useError } from '../home/ErrorContext';
import { ChooseChallenge } from './choose';
import { CodeChallenge } from './code';



export const ChallengeScreen = () => {
    const navigate = useNavigate();
    const { articleId } = useParams();
    const [challenges, setChallenges] = useState<ChallengeResponse[]>();
    const { errorMessages, setError } = useError();
    const location = useLocation();
    const challenge: ChallengeResponse = location.state?.challenge;

    type ChallengeContent = {
        correctAnswer: string;
    }
    const challengeContent: ChallengeContent = JSON.parse(challenge.content);


    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">
                {challenge && (
                    <div>
                        {challengeContent.correctAnswer}
                        {challenge.type === 'CLOSED' && (
                            <ChooseChallenge
                                title={challenge.name}
                                question={challenge.question}
                                answerOk="42"
                                answer2="43"
                                answer3="44"
                                answer4="45"
                            />
                        )}
                        {challenge.type === 'CODE' && (
                            <CodeChallenge
                                title={challenge.name}
                                question={challenge.question}
                                codeTemplate={"def test():\n    pass # make this function return the answer to life, the universe and everything"}
                            />
                        )}
                    </div>
                )}
            </Container>
        </AppWrapper>
    );

};
