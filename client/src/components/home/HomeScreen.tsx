import React, { useContext } from "react";
import {
  Container,
  Button,
  Row,
  ButtonGroup,
  Card,
  Form,
  InputGroup,
} from "react-bootstrap";
import { ThemeContext } from "../themes/ThemeProvider";
import "../../App.css";

import { markdownTest } from "./markdownTest";
import { AppWrapper } from "./AppWrapper";
import { AuthContext } from "../auth/AuthContext";
import { MarkDownRenderer } from "../common/markdown/MarkDownRenderer";
import { ChooseChallenge } from "../common/challenges/choose";
import { CodeChallenge } from "../common/challenges/code";

type Props = {};

const HomeScreen = (props: Props) => {
  const { theme, toggleTheme } = useContext(ThemeContext);

  return (
    <>
      <AppWrapper>
        <Container className="pt-3">
          <Row>
            <div className="w25 my-3">
              <Button
                onClick={() => {
                  toggleTheme();
                }}
              >
                {theme === "light" ? "Dark" : "Light"} Theme
              </Button>

              <Button
                onClick={() => {
                  toggleTheme();
                }}
                variant="secondary"
              >
                Secondary button
              </Button>
            </div>
          </Row>
          <Row>
            <div className="w25 my-3">
              <ChooseChallenge
                title="Choose Challenge"
                question={"What is the answer to life, the universe and everything?\n `codetest`\n ```py \n print('hello')\n ```"}
                answerOk="42"
                answer2="43"
                answer3="44"
                answer4="45"
              />
            </div>
          </Row>
          <Row>
            <div className="w25 my-3">
              <CodeChallenge 
                title="Code Challenge"
                question={"What is the answer to life, the universe and everything?\n `codetest`\n ```py \n print('hello')\n ```"}
                codeTemplate={"def test():\n    pass # make this function return the answer to life, the universe and everything"}
              />
            </div>
          </Row>
          <Row>
            <div className="w25 my-3">
              <ButtonGroup aria-label="Button group">
                <Button variant="secondary">Left</Button>
                <Button variant="secondary">Middle</Button>
                <Button variant="secondary">Right</Button>
              </ButtonGroup>
            </div>
          </Row>
          <Row>
            <div className="w25 my-3">
              <Card style={{ width: "18rem" }}>
                <Card.Body>
                  <Card.Title>Card Title</Card.Title>
                  <Card.Subtitle className="mb-2 text-muted">
                    Card Subtitle
                  </Card.Subtitle>
                  <Card.Text>
                    Some quick example text to build on the card title and make
                    up the bulk of the card's content.
                  </Card.Text>
                  <Card.Link href="#">Card Link</Card.Link>
                  <Card.Link href="#">Another Link</Card.Link>
                </Card.Body>
              </Card>
            </div>
          </Row>
          <Row>
            <div className="w25 my-3">
              <Form>
                <Form.Group
                  className="mb-3"
                  controlId="exampleForm.ControlInput1"
                >
                  <Form.Label>Email address</Form.Label>
                  <Form.Control type="email" placeholder="name@example.com" />
                </Form.Group>
                <Form.Group
                  className="mb-3"
                  controlId="exampleForm.ControlTextarea1"
                >
                  <Form.Label>Example textarea</Form.Label>
                  <Form.Control as="textarea" rows={3} />
                </Form.Group>
              </Form>
            </div>
          </Row>
          <Row>
            <div className="w25 my-3">
              <>
                <InputGroup className="mb-3">
                  <InputGroup.Text id="basic-addon1">@</InputGroup.Text>
                  <Form.Control
                    placeholder="Username"
                    aria-label="Username"
                    aria-describedby="basic-addon1"
                  />
                </InputGroup>

                <InputGroup className="mb-3">
                  <Form.Control
                    placeholder="Recipient's username"
                    aria-label="Recipient's username"
                    aria-describedby="basic-addon2"
                  />
                  <InputGroup.Text id="basic-addon2">
                    @example.com
                  </InputGroup.Text>
                </InputGroup>

                <Form.Label htmlFor="basic-url">Your vanity URL</Form.Label>
                <InputGroup className="mb-3">
                  <InputGroup.Text id="basic-addon3">
                    https://example.com/users/
                  </InputGroup.Text>
                  <Form.Control
                    id="basic-url"
                    aria-describedby="basic-addon3"
                  />
                </InputGroup>

                <InputGroup className="mb-3">
                  <InputGroup.Text>$</InputGroup.Text>
                  <Form.Control aria-label="Amount (to the nearest dollar)" />
                  <InputGroup.Text>.00</InputGroup.Text>
                </InputGroup>

                <InputGroup>
                  <InputGroup.Text>With textarea</InputGroup.Text>
                  <Form.Control as="textarea" aria-label="With textarea" />
                </InputGroup>
              </>
            </div>
          </Row>

          <Row>
            <div className="w25 my-3">
              <MarkDownRenderer content={markdownTest}/>
            </div>
          </Row>
        </Container>
      </AppWrapper>
    </>
  );
};
export default HomeScreen;
