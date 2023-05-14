import { ReactNode, useContext, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../../home/AppWrapper';



export const ChallengesScreen = () => {
    const navigate = useNavigate();
    const { articleId } = useParams();

    return (
        <AppWrapper hideSidebar>
            <Container className="my-5">
            </Container>
        </AppWrapper>
    );
};

