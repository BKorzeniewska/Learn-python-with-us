import { Alert, Button, Container, Form, Row } from "react-bootstrap";
import "../../App.css";
import { AppWrapper } from "../common/AppWrapper";
import { authenticate, passowrdRecoveryRequest } from "./apis/login";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";
import { useContext, useState } from "react";
import axios from "axios";
import { useError } from "../common/ErrorContext";



export const PasswordRecoveryScreen = () => {
    const navigate = useNavigate();
    const { setToken, setRole } = useContext(AuthContext);
    const { errorMessages, setError } = useError();

    return (
        <>
            <AppWrapper hideSidebar>
                <Container fluid>
                <div className="form-container">
                    <Row>
                        <div className="m-auto my-3 text-center">
                            <h1>Odzyskiwanie konta</h1>
                        </div>
                    </Row>
                    <Row>
                        <div className="m-auto w-75 my-3">
                            <Form
                                onSubmit={(e) => {
                                    e.preventDefault();

                                    const response = passowrdRecoveryRequest(e.currentTarget.email.value.toString().trim());
                                    response.then((data) => {
                                        if(data.isOk) {
                                            navigate("/password-recovery-next");
                                        } else {    
                                            setError('Nie udało się odzyskać hasła. Spróbuj ponownie.');
                                        }
                            
                                    });
                                }}
                            >
                                <Form.Group
                                    className="mb-3"
                                    controlId="email"
                                >
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control type="email" placeholder="name@example.com" />
                                </Form.Group>

                                <Form.Group>
                                    <Button variant="primary" type="submit" className="w-100 mb-3">
                                        Odzyskaj
                                    </Button>
                                </Form.Group>

                            </Form>
                        </div>
                    </Row>
                    </div>
                </Container>
            </AppWrapper>
        </>

    )
}