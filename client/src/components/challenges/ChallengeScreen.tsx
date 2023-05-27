import { ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../home/AppWrapper';
import { ChallengeResponse, getChallengesByArticleId } from './apis/challenge';
import { useError } from '../home/ErrorContext';
import { ChooseChallenge } from './choose';
import { CodeChallenge } from './code';
import { OpenChallenge } from './open';

type OpenChallengeContent = {
    correctAnswer: string;
}
export type PossibleAnswers = {
    A: string;
    B: string;
    C: string;
    D: string;
}
type ClosedChallengeContent = {
    correctAnswer: string;
    possibleAnswers: PossibleAnswers;
}
type CodeChallengeContent = {
    correctAnswer: string;
}

type ChallengeContent = OpenChallengeContent | ClosedChallengeContent | CodeChallengeContent;


export const ChallengeScreen = () => {
    const navigate = useNavigate();
    const { articleId } = useParams();
    const [challenges, setChallenges] = useState<ChallengeResponse[]>();
    const { errorMessages, setError } = useError();
    const location = useLocation();
    const challenge: ChallengeResponse | undefined = location.state?.challenge;


    let challengeContent: ChallengeContent = JSON.parse(challenge?.content || '{"correctAnswer": "42"}');

    console.log(challengeContent);

    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">
                {challenge && (
                    <div>
                        {challenge.type === 'CLOSED' && (

                            <ChooseChallenge
                                id={challenge.id}
                                title={challenge.name}
                                question={challenge.question}
                                possibleAnswers={(challengeContent as ClosedChallengeContent).possibleAnswers}
                            />
                        )}
                        {challenge.type === 'CODE' && (
                            <CodeChallenge
                                id={challenge.id}
                                title={challenge.name}
                                question={challenge.question}
                                codeTemplate={"def test():\n    pass # make this function return the answer to life, the universe and everything"}
                            />
                        )}
                        {challenge.type === "OPEN" && (
                            <OpenChallenge
                                id={challenge.id}
                                title={challenge.name}
                                question={challenge.question}
                            />
                        )}
                    </div>
                )}
            </Container>
        </AppWrapper>
    );

};
