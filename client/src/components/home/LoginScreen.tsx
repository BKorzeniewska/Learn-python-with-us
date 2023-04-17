import { Button, Container, Form, Row } from "react-bootstrap";
import "../../App.css";
import { AppWrapper } from "./AppWrapper";
import { authenticate } from "../common/apis/login";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../auth/AuthContext";
import { useContext } from "react";
import axios from "axios";


type LoginFormData = {
    email: string;
    password: string;
}

export const LoginScreen = () => {
    const navigate = useNavigate();
    const { setToken } = useContext(AuthContext);


    const handleSubmit = (e: LoginFormData) => {

        const response = authenticate(e.email, e.password);
        response.then((data) => {
            if(data.isOk) {
                setToken(data.value.token)
            } else {          
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
                <Container>
                    <Row>
                        <div className="m-auto w-25 my-3 text-center">
                            <h1>Login</h1>
                        </div>
                    </Row>
                    <Row>
                        <div className="m-auto w-50 my-3">
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
                                    <Button variant="primary" type="submit" className="w-100 mb-1">
                                        Zaloguj
                                    </Button>
                                    <Button variant="secondary" type="button" className="w-100 my-3" onClick={() => navigate("/register")}>
                                        Zarejestruj
                                    </Button>
                                </Form.Group>

                            </Form>
                        </div>
                    </Row>
                </Container>
            </AppWrapper>
        </>

    )
}