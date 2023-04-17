import axios from "axios";
import { useContext } from "react";
import { Button, Container, Form, Row } from "react-bootstrap";
import "../../App.css";
import { AuthContext } from "../auth/AuthContext";
import { register, registerRequest } from "../common/apis/login";
import { AppWrapper } from "./AppWrapper";

export const RegisterScreen = () => {

    const { token, setToken } = useContext(AuthContext);

    const handleSubmit = (e: registerRequest) => {
        const response = register(e);
        response.then((data) => {
            if(data.isOk) {
                setToken(data.value.token)
            } else {          
            }
        });

        // print response
        console.log(response);
    }

    return (
        <>
            <AppWrapper>
                <Container>
                    <Row>
                        <div className="m-auto w-25 my-3 text-center">
                            <h1>Zaloguj się</h1>
                        </div>
                    </Row>
                    <Row>
                        <div className="m-auto w-50 my-3">
                            <Form
                                onSubmit={(e) => {
                                    e.preventDefault();

                                    const formData: registerRequest = {
                                        email: e.currentTarget.email.value,
                                        password: e.currentTarget.pass.value,
                                        lastname: e.currentTarget.lastname.value,
                                        firstname: e.currentTarget.firstname.value,
                                        nickname: e.currentTarget.nick.value,
                                    };

                                    handleSubmit(formData);
                                }}
                            >
                                <Form.Group
                                    className="mb-4"
                                    controlId="nick"
                                >
                                    <Form.Label>Nickname</Form.Label>
                                    <Form.Control type="text" placeholder="Pedro" />
                                </Form.Group>
                                <Form.Group
                                    className="mb-4"
                                    controlId="firstname"
                                >
                                    <Form.Label>Imię</Form.Label>
                                    <Form.Control type="text" placeholder="Pedro" />
                                </Form.Group>
                                <Form.Group
                                    className="mb-4"
                                    controlId="lastname"
                                >
                                    <Form.Label>Nazwisko</Form.Label>
                                    <Form.Control type="text" placeholder="Pedro" />
                                </Form.Group>
                                <Form.Group
                                    className="mb-4"
                                    controlId="email"
                                >
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control type="email" placeholder="name@example.com" />
                                </Form.Group>
                                <Form.Group
                                    className="mb-4"
                                    controlId="pass"
                                >
                                    <Form.Label>Hasło</Form.Label>
                                    <Form.Control type="password" />
                                </Form.Group>
                                <Form.Group
                                    className="mb-4"
                                    controlId="pass"
                                >
                                    <Form.Label>Powtórz Hasło</Form.Label>
                                    <Form.Control type="password" />
                                </Form.Group>
                                <Form.Group>
                                    <Button variant="primary" type="submit" className="w-100 mb-1">
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