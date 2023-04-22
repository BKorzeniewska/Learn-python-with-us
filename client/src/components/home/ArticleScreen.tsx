import { useState } from "react";
import { useParams } from "react-router-dom"
import { Article } from "../common/apis/article";
import { AppWrapper } from "./AppWrapper";
import { Col, Container, Row } from "react-bootstrap";
import { LoadingSpinner } from "./Spinner";
import "../../App.css";
import "bootstrap/dist/css/bootstrap.min.css";

export const AcrticleScreen = () => {
    const { articleId } = useParams();
    const params = new URLSearchParams(window.location.pathname);
    const articleName = params.get('articleName');

    const [isLoading, setisLoading] = useState(true);
    const [article, setArticle] = useState<Article|null>(null);

    return (
        <>
            <AppWrapper>
                <Container className="mt-5" fluid>
                    <Row>
                        <Col>
                            <Row>
                                <div className="m-auto my-3 text-center">
                                    <h1>{articleName}</h1>
                                </div>
                            </Row>
                            <Row className="m-auto">
                            <div className="m-auto my-3 text-center">
                            <LoadingSpinner isLoading={isLoading}>
                        
                    
                            </LoadingSpinner>
                            </div>
                            </Row>
                        </Col>
                    </Row>
                </Container>
            </AppWrapper>
        </>
    )


    

}