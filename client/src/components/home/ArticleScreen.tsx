import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom"
import { Article, loadArticleById } from "../common/apis/article";
import { AppWrapper } from "./AppWrapper";
import { Button, Col, Container, Row } from "react-bootstrap";
import { LoadingSpinner } from "./Spinner";
import "../../App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { MarkDownRenderer } from "../common/markdown/MarkDownRenderer";

export const AcrticleScreen = () => {
    const { articleId } = useParams();
    const params = new URLSearchParams(window.location.pathname);
    const articleName = params.get('articleName');

    const [isLoading, setisLoading] = useState(true);
    const [article, setArticle] = useState<Article | null>(null);

    useEffect(
        () => {
            loadArticleById(articleId || "0").then(
                (article) => {
                    if (article.isOk) {
                        setArticle(article.value);
                    } else {
                        setArticle(
                            {
                                id: 0,
                                title: "Nie udało się wczytać strony",
                                content: `# Ups! Coś poszło nie tak\n\`\`\`py\n# TODO fix this... \nraise Exception("Nie udało się wczytać strony")\n\`\`\` \n ## Ale nie martw się \n Wszystko będzie dobrze`,
                                chapterId: 0,
                                userId: 0,
                                date: new Date().toISOString(),
                                nextArticle: 0,
                                previousArticle: 0,
                            });
                    }
                }
            ).finally(
                () => {
                    setisLoading(false);
                }
            )
        }, [articleId]
    );

    return (
        <>
            <AppWrapper>
                <Container className="mt-5" style={{ minHeight: "100vh" }}>
                    <Row>
                        <div className="m-auto my-3 text-center">
                            <h1>{articleName}</h1>
                        </div>
                    </Row>
                    <Row>
                        <div className="m-auto my-3">
                            <LoadingSpinner isLoading={isLoading}>
                                <Row className="m-auto">
                                    <div className="m-auto my-3">
                                        <MarkDownRenderer content={article?.content!!} />
                                    </div>
                                </Row>
                                <Row className="my-3 justify-content-between">
                                    <Col className="col-3">
                                        <Button variant="primary">Poprzedni artykuł</Button>

                                    </Col>
                                    <Col className="col-3">
                                        <Button
                                            style={{ float: "right" }}//TODO: can casue problems
                                            variant="primary"
                                            href="/article/{}"
                                        >Następny artykuł</Button>
                                    </Col>

                                </Row>
                            </LoadingSpinner>
                        </div>
                    </Row>
                </Container>
            </AppWrapper>
        </>
    )
}