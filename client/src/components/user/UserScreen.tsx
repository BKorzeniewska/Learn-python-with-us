import { Alert, Button, Container, Row, Col, Card } from "react-bootstrap";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { useError } from "../home/ErrorContext";
import { UserInfo, loadUserById } from "./apis/profile";
import { AppWrapper } from "../home/AppWrapper";
import "./user-screen.css"
import "hsv-rgb"

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

  const cyrb53 = (str: string, seed: number = 0) => {
    let h1 = 0xdeadbeef ^ seed, h2 = 0x41c6ce57 ^ seed;
    for (let i = 0, ch; i < str.length; i++) {
      ch = str.charCodeAt(i);
      h1 = Math.imul(h1 ^ ch, 2654435761);
      h2 = Math.imul(h2 ^ ch, 1597334677);
    }
    h1 = Math.imul(h1 ^ (h1 >>> 16), 2246822507);
    h1 ^= Math.imul(h2 ^ (h2 >>> 13), 3266489909);
    h2 = Math.imul(h2 ^ (h2 >>> 16), 2246822507);
    h2 ^= Math.imul(h1 ^ (h1 >>> 13), 3266489909);

    return 4294967296 * (2097151 & h2) + (h1 >>> 0);
  };

  function generateRandomPixels(str: string): string {
    const width = 4; // width of the image
    const height = 4; // height of the image
    const imageData = new Uint8ClampedArray(width * height * 4); // create an empty buffer for image data
    var rgb = require('hsv-rgb');

    // fill the buffer with random pixel values
    for (let i = 0; i < imageData.length; i += 4) {
      let val1 = (cyrb53(str, i) % 100) / 100;
      let val2 = (cyrb53(str, i + 234) % 100) / 100;

      let [r, g, b] = rgb(70 * val1 + 240, 75, 30 * val2 + 80);
      imageData[i] = Math.floor(r); // red
      imageData[i + 1] = Math.floor(g); // green
      imageData[i + 2] = Math.floor(b); // blue
      imageData[i + 3] = 255; // alpha
    }

    // create a canvas element to draw the image
    const canvas = document.createElement('canvas');
    canvas.width = width;
    canvas.height = height;
    const ctx = canvas.getContext('2d')!;
    const imageDataObj = new ImageData(imageData, width, height);

    // draw the image on the canvas
    ctx.putImageData(imageDataObj, 0, 0);

    // encode the canvas as a base64 PNG
    const dataUrl = canvas.toDataURL('image/png');
    const base64Data = dataUrl.replace(/^data:image\/png;base64,/, '');

    return base64Data;
  }

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
                      // src="https://via.placeholder.com/150"
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
                      <strong>Experience Points: </strong>
                      {result.exp}
                    </p>
                    <p>
                      <strong>Challenges Solved: </strong>
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
