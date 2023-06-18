import { ReactNode, useContext, useEffect, useRef, useState } from 'react';
import { Button, Col, Container, Form, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Article, ArticleMenu, CreateArticleRequest, ModifyArticleRequest, createArticle, loadArticleById, loadArticleMenu, modifyArticle } from '../../article/apis/article';
import { useError } from '../../common/ErrorContext';
import { ThemeContext } from '../../themes/ThemeProvider';
import { AppWrapper } from '../../common/AppWrapper';
import { MarkDownRenderer } from '../../common/markdown/MarkDownRenderer';
import { LoadingSpinner } from '../../common/Spinner';
import { ChallengeResponse, ChallengeType, loadChallengeById } from '../../challenges/apis/challenge';
import "./challenge.css"
import { ChallengeContent, ClosedChallengeContent, CodeChallengeContent, OpenChallengeContent } from '../../challenges/ChallengeScreen';
import { CreateChallengeRequest, ModifyChallengeRequest, createChallenge, modifyChallenge } from './apis/challenges';





export const ChallengeEditionScreen = () => {
    const navigate = useNavigate();
    const { errorMessages, setError } = useError();
    const { challengeId } = useParams();
    const [challenge, setChallenge] = useState<ChallengeResponse>();
    const [isLoading, setIsLoading] = useState(false);
    const [selectedType, setSelectedType] = useState<ChallengeType>("OPEN");
    const [menu, setMenu] = useState<ArticleMenu[]>();
    const [selectedArticles, setSelectedArticles] = useState<string[]>([]);


    const [challengeContent, setChallengeContent] = useState<ChallengeContent>({
        possibleAnswers: {
            A: "",
            B: "",
            C: "",
            D: ""
        },
        correctAnswer: ""
    });

    const location = useLocation();
    useEffect(() => {
        loadArticleMenu().then((data) => {
            if (data.isOk) {
                setMenu(data.value);
            } else {
                setError("Nie udało się załadować listy artykułów");
            }
        });
    }, [location]);

    useEffect(() => {
        if (challengeId) {
            //convert challengeId to number
            const id = Number(challengeId);

            loadChallengeById(id).then((challenge) => {
                if (challenge.isOk) {
                    setSelectedType(challenge.value.type);
                    setChallenge(challenge.value);
                    setChallengeContent(JSON.parse(challenge.value.content));
                }
                else {
                    console.log(challenge.error);
                    setError('Nie udało się usunąć artykułu');
                }
            });
        }
    }, [challengeId]);



    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">

                <Form onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
                    event.preventDefault();
                    setIsLoading(true);
                    let parsedContent: string = JSON.stringify(challengeContent);
                    if (selectedType === "CLOSED") {
                        let closed: ClosedChallengeContent = {
                            possibleAnswers: {
                                A: (event.target as any).elements.formA.value,
                                B: (event.target as any).elements.formB.value,
                                C: (event.target as any).elements.formC.value,
                                D: (event.target as any).elements.formD.value,
                            },
                            correctAnswer: (event.target as any).elements.formAnswer.value,
                        }
                        parsedContent = JSON.stringify(closed);
                    } else if (selectedType === "OPEN") {
                        let open: OpenChallengeContent = {
                            correctAnswer: (event.target as any).elements.formAnswer.value,
                        };
                        parsedContent = JSON.stringify(open);
                    } else if (selectedType === "CODE") {
                        let code: CodeChallengeContent = {
                            correctAnswer: (event.target as any).elements.formAnswer.value,
                        }
                        parsedContent = JSON.stringify(code);
                    }

                    if (challengeId === undefined) {
                        let out: CreateChallengeRequest = {
                            name: event.currentTarget.formTitle.value,
                            type: selectedType,
                            content: parsedContent,
                            visible: true,
                            articlesID: selectedArticles.map(Number),
                            question: event.currentTarget.formQuestion.value,
                            exp: 10,
                        }
                        createChallenge(out).then((response) => {
                            if (response.isOk) {
                                navigate(-1);
                            }
                            else {
                                console.log(response.error);
                                setError('Nie udało się utworzyć zadania');
                            }
                        });
                    }
                    else {
                        let out: ModifyChallengeRequest = {
                            id: Number(challengeId),
                            name: event.currentTarget.formTitle.value,
                            type: selectedType,
                            content: parsedContent,
                            visible: true,
                            articlesID: selectedArticles.map(Number),
                            question: event.currentTarget.formQuestion.value,
                            exp: 10,
                        }
                        modifyChallenge(out).then((response) => {
                            if (response.isOk) {
                                navigate(-1);
                            }
                            else {
                                console.log(response.error);
                                setError('Nie udało się utworzyć zadania');
                            }
                        });
                    }
                    console.log(Array.from((event.target as any).elements.formArticlesId.value))
                    console.log((event.target as any).elements.formArticlesId.value, "submit");
                    setIsLoading(false);
                }}>
                    <Form.Group className="mb-3" controlId="formTitle">
                        <Form.Label>Nazwa:</Form.Label>
                        <Form.Control type="text" placeholder="Wprowadź tytuł" defaultValue={challenge?.name} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formArticlesId">
                        <Form.Label>Artykuły:</Form.Label>
                        <Form.Select
                            multiple
                            aria-label="Wybierz rozdział"
                            defaultValue={challenge?.articlesID!.map(String)}
                            onChange={(event) => {
                                const selectedOptions = Array.from(event.target.selectedOptions);
                                const selectedValues = selectedOptions.map((option) => option.value);
                                setSelectedArticles(selectedValues);
                            }}
                        >
                            {menu?.map((item) =>
                                item.articles.map((article) => (
                                    <option key={article.id} value={article.id}>
                                        {article.title}
                                    </option>
                                ))
                            )}
                        </Form.Select>
                    </Form.Group>


                    <Form.Group className="mb-3" controlId="formQuestion">
                        <Form.Label>Pytanie:</Form.Label>
                        <Form.Control
                            as="textarea"
                            style={{ fontFamily: "monospace" }}
                            rows={3}
                            defaultValue={challenge?.question}
                        // onChange={(event) => setCurrentText(event.target.value)}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formArticlesId">
                        <Form.Label>Typ zadania:</Form.Label>
                        <Form.Select aria-label="Wybierz rozdział" defaultValue={challenge?.type.toString()}
                            onChange={(event) => {

                                const selectedType = event.target.value;
                                setSelectedType(selectedType as ChallengeType);
                            }}>
                            <option value={"OPEN"}>OPEN</option>
                            <option value={"CLOSED"}>CLOSED</option>
                            <option value={"CODE"}>CODE</option>
                        </Form.Select>
                    </Form.Group>

                    {selectedType === "CLOSED" &&
                        <div>
                            <Form.Group className="mb-3" controlId="formA">
                                <Form.Label>A:</Form.Label>
                                <Form.Control type="text" placeholder="Wprowadż odpowiedź A" defaultValue={(challengeContent as ClosedChallengeContent).possibleAnswers.A} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formB">
                                <Form.Label>B:</Form.Label>
                                <Form.Control type="text" placeholder="Wprowadż odpowiedź B" defaultValue={(challengeContent as ClosedChallengeContent).possibleAnswers.B} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formC">
                                <Form.Label>C:</Form.Label>
                                <Form.Control type="text" placeholder="Wprowadż odpowiedź C" defaultValue={(challengeContent as ClosedChallengeContent).possibleAnswers.C} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formD">
                                <Form.Label>D:</Form.Label>
                                <Form.Control type="text" placeholder="Wprowadż odpowiedź D" defaultValue={(challengeContent as ClosedChallengeContent).possibleAnswers.D} />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formAnswer">
                                <Form.Label>Poprawna:</Form.Label>
                                <Form.Select aria-label="Poprawna" defaultValue={(challengeContent as ClosedChallengeContent).correctAnswer}>

                                    <option value={"A"}>A</option>
                                    <option value={"B"}>B</option>
                                    <option value={"C"}>C</option>
                                    <option value={"D"}>D</option>
                                </Form.Select>
                            </Form.Group>
                        </div>
                    }
                    {selectedType === "OPEN" &&
                        <Form.Group className="mb-3" controlId="formAnswer">
                            <Form.Label>Prawidłowa odpowiedź:</Form.Label>
                            <Form.Control
                                as="textarea"
                                style={{ fontFamily: "monospace" }}
                                rows={3}
                                defaultValue={(challengeContent as OpenChallengeContent).correctAnswer}
                            // onChange={(event) => setCurrentText(event.target.value)}
                            />
                        </Form.Group>
                    }
                    {selectedType === "CODE" &&
                        <Form.Group className="mb-3" controlId="formAnswer">
                            <Form.Label>Odpowiedz:</Form.Label>
                            <Form.Control
                                as="textarea"
                                style={{ fontFamily: "monospace" }}
                                rows={3}
                                defaultValue={""}
                            // onChange={(event) => setCurrentText(event.target.value)}
                            />
                        </Form.Group>
                    }

                    <Form.Group>
                        <Button variant="primary" type="submit" className="submit-button" disabled={isLoading}>
                            <LoadingSpinner isLoading={isLoading}>Submit</LoadingSpinner>
                        </Button>
                    </Form.Group>
                </Form>
            </Container>
        </AppWrapper>
    );
};

