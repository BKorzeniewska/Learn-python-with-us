import { Alert, Button, Container, Form, Row } from "react-bootstrap";
import "../../App.css";
import { AppWrapper } from "../common/AppWrapper";
import { authenticate } from "./apis/login";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";
import { useContext, useState } from "react";
import axios from "axios";
import { useError } from "../common/ErrorContext";


type LoginFormData = {
    email: string;
    password: string;
}

export const LoginScreen = () => {
    const navigate = useNavigate();
    const { setToken, setRole, setUser } = useContext(AuthContext);
    const { errorMessages, setError } = useError();



    const handleSubmit = (e: LoginFormData) => {

        const response = authenticate(e.email, e.password);
        response.then((data) => {
            if(data.isOk) {
                setToken(data.value.token);
                setRole(data.value.role);
                setUser(data.value);
                navigate("/");
            } else {    
                setError('Nie udało się zalogować. Spróbuj ponownie.');
            }

        });
        // print response
        console.log(response);
    }
    
    //todo: validate form data
    //todo: display error message

    return (
        <>
            <AppWrapper hideSidebar>
                <Container fluid>
                <div className="form-container">
                    <Row>
                        <div className="m-auto my-3 text-center">
                            <h1>Zaloguj się</h1>
                        </div>
                    </Row>
                    <Row>
                        <div className="m-auto w-75 my-3">
                            <Form
                                onSubmit={(e) => {
                                    e.preventDefault();

                                    const formData: LoginFormData = {
                                        email: e.currentTarget.email.value,
                                        password: e.currentTarget.pass.value,
                                    };

                                    handleSubmit(formData);
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
                                    controlId="pass"
                                >
                                    <Form.Label>Hasło</Form.Label>
                                    <Form.Control type="password" />
                                </Form.Group>
                                <Form.Group>
                                    <Button variant="primary" type="submit" className="w-100 mb-3">
                                        Zaloguj
                                    </Button>
                                    Nie masz jeszcze konta?
                                    <Button variant="secondary" type="button" className="w-100 mb-3" onClick={() => navigate("/register")}>
                                        Zarejestruj
                                    </Button>
                                    <p className="forgot-password-text" onClick={() => navigate("/password-recovery")}>
                                        Zapomniałeś hasła?
                                    </p>
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