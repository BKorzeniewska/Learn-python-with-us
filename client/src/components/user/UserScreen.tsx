import { Alert, Button, Container, Row, Col, Card } from "react-bootstrap";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { useError } from "../home/ErrorContext";
import { UserInfo, loadUserById } from "./apis/profile";
import { AppWrapper } from "../home/AppWrapper";
import "./user-screen.css"
import "hsv-rgb"
import { generateRandomPixels } from "./avatarGen";

export const UserScreen = () => {
  const navigate = useNavigate();
  const { errorMessages, setError } = useError();
  const [result, setResult] = useState<UserInfo>();
  const { userId } = useParams();

  const location = useLocation();
  useEffect(() => {
    if (userId) {
      loadUserById(userId).then((data) => {
        if (data.isOk) {
          setResult(data.value);
        } else {
          setError("Nie udało się załadować informacji o użytkowniku");
        }
      });
    }
    else {
      loadUserById().then((data) => {
        if (data.isOk) {
          setResult(data.value);
        } else {
          setError("Nie udało się załadować informacji o użytkowniku");
        }
      });
    }
  }, [userId]);

 

  return (
    <AppWrapper hideSidebar>
      <Container className="my-5">
        {result ? (
          <Row className="justify-content-center">
            <Col md={8} lg={6}>
              <Card className="shadow-sm">
                <Card.Header className="border-bottom-0">
                  <h3 className="text-center my-3">{`${result.firstName} ${result.lastName}`}</h3>
                </Card.Header>
                <Card.Body className="">
                  <div className="d-flex flex-column justify-content-center align-items-center my-3">
                    <img
                      src={`data:image/png;base64,${generateRandomPixels(result.nickname + result.email)}`}
                      alt="profile-picture"
                      className="rounded-circle mb-3 profile-picture"
                    />
                    <h5 className="text-center">{result.nickname}</h5>
                    <p className="text-center">{result.email}</p>
                  </div>
                  <hr />
                  <div className="d-flex flex-column">
                    <p>
                      <strong>Level: </strong>
                      {result.level}
                    </p>
                    <p>
                      <strong>Punkty doświadczenia: </strong>
                      {result.exp}
                    </p>
                    <p>
                      <strong>Rozwiązane zadania: </strong>
                      {result.challengesSolvedCount}
                    </p>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>) : ("cantLoadUser")}
      </Container>
    </AppWrapper>
  );
};
