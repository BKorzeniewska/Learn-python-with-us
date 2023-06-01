import { ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../home/AppWrapper';
import { ChallengeResponse, ChallengeResult, getChallengesByArticleId } from './apis/challenge';
import { useError } from '../home/ErrorContext';
import { ChooseChallenge } from './choose';
import { CodeChallenge } from './code';
import { OpenChallenge } from './open';
import { BsCheckCircle } from 'react-icons/bs';

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
    const [challengeDone, setChallengeDone] = useState(false); // State to track if challenge was done correctly

    let challengeContent: ChallengeContent = JSON.parse(challenge?.content || '{"correctAnswer": "42"}');

    console.log(challengeContent);

    // Function to handle challenge completion
    const handleChallengeCompletion = (status: ChallengeResult) => {
        if (status === "SUCCESS") {
            setChallengeDone(true); // Set challengeDone to true when challenge is completed
        }
        else {
            setChallengeDone(false);
            setError("Nie udało się wykonać zadania");
        }
    };

    // Function to navigate to the next challenge
    const goToNextChallenge = () => {
        // Implement your logic to navigate to the next challenge
        navigate(-1); // Replace '/next-challenge' with the actual URL or route for the next challenge
    };

    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">
                {challenge && (
                    <div>
                        {challengeDone ? ( // If challengeDone is true, display completion information and button to next challenge
                            <div>
                                <div className='challenge-complete-wrapper'>
                                    <BsCheckCircle size={80} color="green" className="mb-3" /> {/* Add tick sign symbol */}
                                    <p className="mb-3">Zadanie wykonane prawidłowo!</p>
                                    <Button onClick={goToNextChallenge}>Następne zadanie</Button>
                                </div>
                            </div>
                        ) : (
                            <div>
                                {challenge.type === 'CLOSED' && (
                                    <ChooseChallenge
                                        id={challenge.id}
                                        title={challenge.name}
                                        question={challenge.question}
                                        possibleAnswers={(challengeContent as ClosedChallengeContent).possibleAnswers}
                                        onChallengeComplete={handleChallengeCompletion} // Pass the handleChallengeCompletion function to the ChooseChallenge component
                                    />
                                )}
                                {challenge.type === 'CODE' && (
                                    <CodeChallenge
                                        id={challenge.id}
                                        title={challenge.name}
                                        question={challenge.question}
                                        codeTemplate={"def test():\n    pass # make this function return the answer to life, the universe and everything"}
                                        onChallengeComplete={handleChallengeCompletion} // Pass the handleChallengeCompletion function to the CodeChallenge component
                                    />
                                )}
                                {challenge.type === "OPEN" && (
                                    <OpenChallenge
                                        id={challenge.id}
                                        title={challenge.name}
                                        question={challenge.question}
                                        onChallengeComplete={handleChallengeCompletion} // Pass the handleChallengeCompletion function to the OpenChallenge component
                                    />
                                )}
                            </div>
                        )}
                    </div>
                )}
            </Container>
        </AppWrapper>
    );
};
