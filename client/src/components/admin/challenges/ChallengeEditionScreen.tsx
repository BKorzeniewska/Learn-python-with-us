import { ReactNode, useContext, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Article, ArticleMenu, CreateArticleRequest, ModifyArticleRequest, createArticle, loadArticleById, loadArticleMenu, modifyArticle } from '../../common/apis/article';
import { useError } from '../../home/ErrorContext';
import { ThemeContext } from '../../themes/ThemeProvider';
import { AppWrapper } from '../../home/AppWrapper';
import { MarkDownRenderer } from '../../common/markdown/MarkDownRenderer';
import { LoadingSpinner } from '../../home/Spinner';
import { loadChallengeById } from '../../challenges/apis/challenge';
import "./challenge.css"





export const ChallengeEditionScreen = () => {
    const navigate = useNavigate();
    const { errorMessages, setError } = useError();
    const { challengeId } = useParams();



    const location = useLocation();
    useEffect(() => {
        if(challengeId){
            //convert challengeId to number
            const id = Number(challengeId);



            loadChallengeById(id).then((challenge) => {
                console.log(challenge)
            }).catch((error) => {
                setError(error.message);
            })
        }
    }, [challengeId]);
    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">
            </Container>
        </AppWrapper>
    );
};

