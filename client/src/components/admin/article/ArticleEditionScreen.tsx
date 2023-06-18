import { ReactNode, useContext, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Article, ArticleMenu, CreateArticleRequest, ModifyArticleRequest, createArticle, loadArticleById, loadArticleMenu, modifyArticle } from '../../article/apis/article';
import { useError } from '../../common/ErrorContext';
import { ThemeContext } from '../../themes/ThemeProvider';
import { AppWrapper } from '../../common/AppWrapper';
import { MarkDownRenderer } from '../../common/markdown/MarkDownRenderer';
import { LoadingSpinner } from '../../common/Spinner';





export const ArticleEditionScreen = () => {
    const navigate = useNavigate();
    const [currentArticle, setCurrentArticle] = useState<number | null>(null);

    const [currentText, setCurrentText] = useState<string>("");
    const [article, setArticle] = useState<Article>();
    const { errorMessages, setError } = useError();

    const [isLoading, setIsLoading] = useState(false);
    const lineCount = currentText.split("\n").length;
    const { articleId } = useParams();


    const [menu, setMenu] = useState<ArticleMenu[]>();

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


    useEffect(
        () => {
            if (articleId) {
                loadArticleById(articleId || "0").then(
                    (art) => {
                        if (art.isOk) {
                            setArticle(art.value);
                            setCurrentText(art.value.content);
                        } else {
                            setError("Nie udało się wczytać artykułu");
                            setCurrentText("Coś poszło nie tak...");
                            setArticle(

                                {
                                    id: 0,
                                    title: "Nie udało się wczytać artykułu",
                                    content: "Coś poszło nie tak...",
                                    chapterId: 0,
                                    userId: 0,
                                    date: new Date().toISOString(),
                                    visible: false,

                                });
                        }
                        
                    }
                )
            }
            else {
                setArticle(

                    {
                        id: 0,
                        title: "",
                        content: "",
                        chapterId: 0,
                        userId: 0,
                        date: new Date().toISOString(),
                        visible: false,

                    });
                    setCurrentText(article?.content || "");
            }
            
        }, [articleId]
    );

    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">
                <h2>{article?.title}</h2>
                <Row>
                    <Col>
                        <MarkDownRenderer content={currentText} key={currentText}/>
                    </Col>
                    <Col>
                        <Form onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
                            event.preventDefault();
                            setIsLoading(true);

                            if (articleId === undefined) {
                                const request: CreateArticleRequest = {
                                    title: (event.target as any).elements.formTitle.value,
                                    content: (event.target as any).elements.formContent.value,
                                    chapterId: parseInt((event.target as any).elements.formChapterId.value),
                                    visible: true,
                                };
                                console.log(request);
                                createArticle(request).then(
                                    (article) => {
                                        if (article.isOk) {
                                            navigate(`/article/${article.value.id}`);
                                        } else {
                                            setError("Nie udało się utworzyć artykułu");
                                        }
                                    }
                                )
                            }
                            else {
                                const request: ModifyArticleRequest = {
                                    title: (event.target as any).elements.formTitle.value,
                                    content: (event.target as any).elements.formContent.value,
                                    id: parseInt(articleId),
                                    visible: true,
                                };
                                console.log(request);
                                modifyArticle(request).then(
                                    (article) => {
                                        if (article.isOk) {
                                            navigate(`/article/${article.value.id}`);
                                        } else {
                                            setError("Nie udało się utworzyć artykułu");
                                        }
                                    }
                                )

                            }
                            console.log("submit");
                            setIsLoading(false);
                        }}>
                            <Form.Group className="mb-3" controlId="formTitle">
                                <Form.Label>Tytuł:</Form.Label>
                                <Form.Control type="text" placeholder="Wprowadź tytuł" defaultValue={article?.title} />
                            </Form.Group>
                            {articleId === undefined &&
                                <Form.Group className="mb-3" controlId="formChapterId">
                                    <Form.Label>Rozdział:</Form.Label>
                                    <Form.Select aria-label="Wybierz rozdział" defaultValue={article?.chapterId}>
                                        {menu?.map((menu) => (
                                            <option key={menu.name} value={menu.id}>{menu.name}</option>
                                        ))}
                                    </Form.Select>
                                </Form.Group>}

                            <Form.Group className="mb-3" controlId="formContent">
                                <Form.Label>Artykuł:</Form.Label>
                                <Form.Control
                                    as="textarea"
                                    style={{ fontFamily: "monospace" }}
                                    rows={lineCount > 20 ? lineCount + 2 : 20}
                                    defaultValue={article?.content}
                                    onChange={(event) => setCurrentText(event.target.value)}
                                />
                            </Form.Group>
                            <Form.Group>
                                <Button variant="primary" type="submit" className="submit-button" disabled={isLoading}>
                                    <LoadingSpinner isLoading={isLoading}>Submit</LoadingSpinner>
                                </Button>
                            </Form.Group>
                        </Form>
                    </Col>
                </Row>
            </Container>
        </AppWrapper>
    );
};

