import { Alert, Button, Container, Row, Col, Card, Form } from "react-bootstrap";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { useError } from "../common/ErrorContext";
import { ModifyUserRequest, UserInfo, loadUserById, modifyUser } from "./apis/profile";
import { AppWrapper } from "../common/AppWrapper";
import "./user-screen.css"
import "hsv-rgb"
import { LoadingSpinner } from "../common/Spinner";
import { AuthContext } from "../auth/AuthContext";

export const UserEditScreen = () => {
    const navigate = useNavigate();
    const { errorMessages, setError } = useError();
    const location = useLocation();
    const user: UserInfo | undefined = location.state?.result;
    const { setUser, getUser } = useContext(AuthContext);

    const [formData, setFormData] = useState<ModifyUserRequest>({
        firstname: user?.firstName || '',
        nickname: user?.nickname || '',
        lastname: user?.lastName || ''
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        modifyUser(formData).then((res) => {
            if (res.isOk) {
                console.log("User modified successfully");
                let user = getUser();
                if (user) {
                    user.nickname = res.value.nickname;
                    setUser(user);
                }
                navigate(-1);

            } else {
                setError("Nie udało się zmodyfikować użytkownika");
            }
        });
    };

    return (
        <AppWrapper hideSidebar>
            <Container className="my-5" fluid>
                <div className="form-container">
                {user && (
                    <>
                        <h2> Modyfikacja użytkownika</h2>
                        <Form onSubmit={handleSubmit}>
                            <Form.Group>
                                <Form.Label>Imię</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="firstname"
                                    value={formData.firstname}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Nazwisko</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="lastname"
                                    value={formData.lastname}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Nickname</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="nickname"
                                    value={formData.nickname}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                            <Button type="submit" style={{marginTop:"1.5rem"}}>Modyfikuj</Button>
                        </Form>
                    </>
                )}
                </div>
            </Container>
        </AppWrapper>
    );
};