import { Alert, Button, Container, Row, Col, Card, ProgressBar } from "react-bootstrap";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { useError } from "../common/ErrorContext";
import { UserInfo, loadUserById } from "./apis/profile";
import { AppWrapper } from "../common/AppWrapper";
import "./user-screen.css"
import "hsv-rgb"
import { generateRandomPixels } from "./avatarGen";
import { LoadingSpinner } from "../common/Spinner";

export const UserScreen = () => {
  const navigate = useNavigate();
  const { errorMessages, setError } = useError();
  const [result, setResult] = useState<UserInfo>();
  const { userId } = useParams();
  const [isLoading, setisLoading] = useState(true);

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
    setisLoading(false);
  }, [userId]);

  const calculateLevelProgress = () => {
    if (result) {
      const levelProgress = (result.exp % 1000) / 10; // Assuming 1 level = 1000 exp, dividing by 10 to get a percentage
      return levelProgress;
    }
    return 0;
  }; 



  return (
    <AppWrapper hideSidebar>
      <Container className="my-5">
        <LoadingSpinner isLoading={isLoading} >
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
                        <ProgressBar now={calculateLevelProgress()} label={`${calculateLevelProgress()}%`} />
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
                {userId === undefined &&
                  <Button     
                    className="mt-3"  
                    variant="primary" 
                    onClick={() => navigate(`/user/edit`, { state: { result } })}>Edytuj profil</Button>}
              </Col>
            </Row>) : ("cantLoadUser")}
      </LoadingSpinner>
    </Container>
    </AppWrapper >
  );
};
