import { Alert, Button, Container, Form, Row } from "react-bootstrap";
import "../../App.css";
import { AppWrapper } from "../common/AppWrapper";
import { RecoveryRequest, authenticate, passowrdRecovery, passowrdRecoveryRequest } from "./apis/login";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";
import { useContext, useState } from "react";
import axios from "axios";
import { useError } from "../common/ErrorContext";



export const PasswordRecoveryNextScreen = () => {
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
                            Użyj kodu przesłanego na twój adres mailowy<br/>
                            i wpisz nowe hasło!
                        </div>
                    </Row>
                    <Row>
                        <div className="m-auto w-75 my-3">
                            <Form
                                onSubmit={(e) => {
                                    e.preventDefault();
                                    const req: RecoveryRequest ={
                                        email: e.currentTarget.email.value.toString().trim(),
                                        token: e.currentTarget.token.value.toString().trim(),
                                        newPassword: e.currentTarget.password.value.toString()
                                    }

                                    const response = passowrdRecovery(req);
                                    response.then((data) => {
                                        if(data.isOk) {
                                            navigate("/login");
                                            setError('Udało się odzyskać konto, zaloguj się ponownie.');
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
                                <Form.Group
                                    className="mb-3"
                                    controlId="token"
                                >
                                    <Form.Label>Kod</Form.Label>
                                    <Form.Control type="text" placeholder="8412a319-ca66-42e1-9ef6-99374cadf98d" />
                                </Form.Group>
                                <Form.Group
                                    className="mb-3"
                                    controlId="password"
                                >
                                    <Form.Label>Nowe hasło</Form.Label>
                                    <Form.Control type="password" />
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