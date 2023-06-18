import { ReactNode, useContext, useEffect, useState } from 'react';
import { Alert, Badge, Button, Col, Container, Form, ListGroup, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../common/AppWrapper';
import { ChallengeResponse, ChallengeResult, ChangeVisibilityRequest, SolutionRequest, addSolution, changeVisibility, getChallengesByArticleId } from './apis/challenge';
import { useError } from '../common/ErrorContext';
import { ChooseChallenge } from './choose';
import { CodeChallenge } from './code';
import { OpenChallenge } from './open';
import { BsCheck2Circle, BsXCircle } from 'react-icons/bs';
import { AuthContext } from '../auth/AuthContext';

export type OpenChallengeContent = {
    correctAnswer: string;
}
export type PossibleAnswers = {
    A: string;
    B: string;
    C: string;
    D: string;
}
export type ClosedChallengeContent = {
    correctAnswer: string;
    possibleAnswers: PossibleAnswers;
}
export type CodeChallengeContent = {
    correctAnswer: string;
}

export type ChallengeContent = OpenChallengeContent | ClosedChallengeContent | CodeChallengeContent;


export const ChallengeScreen = () => {
    const navigate = useNavigate();
    const { setError } = useError();
    const location = useLocation();
    const challenge: ChallengeResponse | undefined = location.state?.challenge;
    const [challengeDone, setChallengeDone] = useState(false); // State to track if challenge was done correctly
    const [challengeStatus, setChallengeStatus] = useState<ChallengeResult | undefined>(undefined); // State to track if challenge was done correctly
    const { isAuthorized } = useContext(AuthContext);

    let challengeContent: ChallengeContent = JSON.parse(challenge?.content || '{"correctAnswer": "42"}');

    console.log(challengeContent);

    // Function to handle challenge completion
    const handleChallengeCompletion = (status: ChallengeResult, answer: string) => {
        if (status === "SUCCESS") {
            setChallengeDone(true); // Set challengeDone to true when challenge is completed
            
            const req: SolutionRequest = {
                challengeId: challenge?.id!,
                correct: true,
                answer: answer,

            }
            addSolution(req).then((res) => {
                if (res.isOk) {
                    console.log("Solution added");
                } else {
                    setError("Nie udało się zapisać rozwiązania");
                }
            }
            )

        }
        else {
            setChallengeDone(false);
            setError("Nie udało się wykonać zadania");
        }
        setChallengeStatus(status);
    };

    // Function to navigate to the next challenge
    const goToNextChallenge = () => {
        // Implement your logic to navigate to the next challenge
        navigate(-1); // Replace '/next-challenge' with the actual URL or route for the next challenge
    };

    function toggleVisible(): void {
        const req: ChangeVisibilityRequest = {
            challengeId: challenge?.id!,
            visible: !challenge?.visible
        }
        changeVisibility(req).then((res) => {
            if (res.isOk) {
                console.log("Visibility changed");
                navigate(-1);
            } else {
                setError("Nie udało się zmienić widoczności zadania");
            }
        })
    }

    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">
                {challenge && (
                    <div>
                        {challengeStatus === 'SUCCESS' &&
                        <div>
                                <div className='challenge-complete-wrapper'>
                                    <BsCheck2Circle size={80} color="green" className="mb-3" /> {/* Add tick sign symbol */}
                                    <p className="mb-3">Zadanie wykonane prawidłowo!</p>
                                    <Button onClick={goToNextChallenge}>Następne zadanie</Button>
                                </div>
                            </div>
 }    
                        {challengeStatus === "FAIL" &&
                            <div>
                                <div className='challenge-complete-wrapper'>
                                    <BsXCircle size={80} color="red" className="mb-3" /> {/* Add tick sign symbol */}
                                    <p className="mb-3">Zadanie wykonane nieprawidłowo!</p>
                                    <div className='buttons-next'>
                                    <Button onClick={goToNextChallenge}>Następne zadanie</Button>
                                    <Button onClick={() => {setChallengeStatus(undefined)}}>Spróbuj ponownie</Button>
                                    </div>
                                </div>
                            </div>
                        }              
 {challengeStatus === undefined &&
                            <div>
                                {challenge.done &&
                                    <Alert variant="success">
                                        Już wykonałeś to zadanie
                                    </Alert>

                                }
                                {!challenge.visible &&
                                    <Alert variant="warning">
                                        Zadanie nie jest widoczne, poproś administratora o odblokowanie tego zadania
                                    </Alert>
                                }
                                {challenge.type === 'CLOSED' && (
                                    <ChooseChallenge
                                        id={challenge.id}
                                        title={challenge.name}
                                        question={challenge.question}
                                        possibleAnswers={(challengeContent as ClosedChallengeContent).possibleAnswers}
                                        onChallengeComplete={handleChallengeCompletion}
                                    />
                                )}
                                {challenge.type === 'CODE' && (
                                    <CodeChallenge
                                        id={challenge.id}
                                        title={challenge.name}
                                        question={challenge.question}
                                        codeTemplate={"def test():\n    pass # make this function return the answer to life, the universe and everything"}
                                        onChallengeComplete={handleChallengeCompletion}
                                    />
                                )}
                                {challenge.type === "OPEN" && (
                                    <OpenChallenge
                                        id={challenge.id}
                                        title={challenge.name}
                                        question={challenge.question}
                                        onChallengeComplete={handleChallengeCompletion}
                                    />
                                )}
                                {isAuthorized("MODERATOR") &&
                                    <Button onClick={toggleVisible} className='mt-2'>
                                        {challenge.visible ? "Ukryj zadanie" : "Upublicznij zadanie"}
                                    </Button>
                                }
                            </div>
                        }
                    </div>
                )}
            </Container>
        </AppWrapper>
    );
};
