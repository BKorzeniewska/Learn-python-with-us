import { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom"
import { Article, ArticleExtended, ChangeVisibilityRequest, changeVisibility, loadArticleByIdExtended } from "./apis/article";
import { AppWrapper } from "../common/AppWrapper";
import { Alert, Button, Col, Container, Row } from "react-bootstrap";
import { LoadingSpinner } from "../common/Spinner";
import "../../App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { MarkDownRenderer } from "../common/markdown/MarkDownRenderer";
import { CommentSection } from "../comments/CommentSection";
import { AuthContext } from "../auth/AuthContext";
import { useError } from "../common/ErrorContext";

export const AcrticleScreen = () => {
    const { articleId } = useParams();
    const params = new URLSearchParams(window.location.pathname);
    const navigate = useNavigate();

    const [isLoading, setisLoading] = useState(true);
    const [article, setArticle] = useState<ArticleExtended | null>(null);
    const { isAuthorized } = useContext(AuthContext);
    const { setError } = useError();
    const [reload, setReload] = useState(false);

    useEffect(
        () => {
            loadArticleByIdExtended(articleId || "0").then(
                (article) => {
                    if (article.isOk) {
                        setArticle(article.value);
                    } else {
                        setArticle(

                            {
                                article: {
                                    id: 0,
                                    title: "Nie udało się wczytać strony",
                                    content: `# Ups! Coś poszło nie tak\n\`\`\`py\n# TODO fix this... \nraise Exception("Nie udało się wczytać strony")\n\`\`\` \n ## Ale nie martw się \n Wszystko będzie dobrze`,
                                    chapterId: 0,
                                    userId: 0,
                                    date: new Date().toISOString(),
                                    visible: true,
                                },
                                previousArticleIndex: null,
                                nextArticleIndex: null,
                                currentArticle: 0,
                                totalArticles: 0,
                            });
                    }
                }
            ).finally(
                () => {
                    setisLoading(false);
                }
            )
        }, [articleId, reload]
    );

    function toggleVisible(): void {
        const req: ChangeVisibilityRequest = {
            articleId: article?.article.id!!,
            visible: !article?.article.visible!!,
        }
        changeVisibility(req).then((res) => {
            if (res.isOk) {
                console.log("Visibility changed");
                setReload(!reload);

            } else {
                setError("Nie udało się zmienić widoczności artykułu");
            }
        })
    }

    return (
        <>
            <AppWrapper>
                <Container className="mt-5" style={{ minHeight: "100vh" }}>
                    <LoadingSpinner isLoading={isLoading}>
                        {!article?.article.visible &&
                            <Alert variant="warning">
                                Ten artykuł jest ukryty, tylko administratorzy i moderatorzy mogą go zobaczyć
                            </Alert>
                        }
                        <Row>
                            <div className="m-auto my-3 text-center">
                                <h1>{article?.article?.title}</h1>
                            </div>
                        </Row>
                        <Row>
                            <div className="m-auto my-3">


                                <Row className="m-auto">
                                    <div className="m-auto my-3">
                                        <MarkDownRenderer content={article?.article?.content!!} />
                                    </div>
                                </Row>
                                <Row className="my-3 justify-content-between">
                                    <Col className="col-3">
                                        {article && article.previousArticleIndex !== null && (
                                            <Button onClick={() => navigate(`/article/${article.previousArticleIndex}`)} variant="primary">Poprzedni artykuł</Button>)}

                                    </Col>
                                    <Col className="col-3" style={{ textAlign: "center" }}>
                                        <Button
                                            onClick={() => navigate(`/challenges/${article?.article.id}`)} variant="primary">Wykonaj zadania</Button>
                                    </Col>
                                    <Col className="col-3">
                                        {article && article.nextArticleIndex !== null && (
                                            <Button
                                                style={{ float: "right" }}
                                                variant="primary"
                                                onClick={() => navigate(`/article/${article.nextArticleIndex}`)}
                                            >Następny artykuł</Button>
                                        )}
                                    </Col>
                                </Row>
                                {isAuthorized("MODERATOR") &&
                                    <Row className="my-3 ">
                                        <Col style={{ textAlign: "center" }}>
                                            <Button onClick={toggleVisible} className='mt-2'>
                                                {article?.article.visible ? "Ukryj atrykuł" : "Upublicznij artykuł"}
                                            </Button>
                                        </Col>
                                    </Row>
                                }
                                <Row>
                                    <CommentSection articleId={article?.article.id!!}></CommentSection>
                                </Row>
                            </div>
                        </Row>
                    </LoadingSpinner>
                </Container>
            </AppWrapper>
        </>
    )
}