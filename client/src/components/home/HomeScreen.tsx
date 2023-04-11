import React, { useContext } from "react";
import {
  Container,
  Navbar,
  Nav,
  Button,
  Row,
  ButtonGroup,
  Card,
  Form,
  InputGroup,
} from "react-bootstrap";
import { MainNavbar } from "../common/layout/MainNavbar";
import { ThemeContext } from "../themes/ThemeProvider";
import "../../App.css";
import ReactMarkdown from "react-markdown";
import remarkParse from 'remark-parse'
import remarkGfm from 'remark-gfm'
import {  Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import { CodeProps } from "react-markdown/lib/ast-to-react";
import { oneDark } from 'react-syntax-highlighter/dist/cjs/styles/prism';
import { markdownTest } from "./markdownTest";

type Props = {};


const HomeScreen = (props: Props) => {
  const { theme, toggleTheme } = useContext(ThemeContext);

  return (
    <>
      <MainNavbar />
      <div id="App" data-theme={theme}>
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
              <ReactMarkdown
                children={markdownTest}
                remarkPlugins={[remarkParse,[remarkGfm]]}
                components={{
                  code({ node, inline, className, children, style, ...props }: CodeProps) {
                    const match = /language-(\w+)/.exec(className || "");
                    return !inline && match ? (
                      <SyntaxHighlighter
                        children={String(children).replace(/\n$/, "")}
                        language={match[1]}
                        style={oneDark}
                        PreTag="div"
                        {...props}
                      />
                    ) : (
                      <code className={className} {...props}>
                        {children}
                      </code>
                    );
                  },
                }}
              />
            </div>
          </Row>
        </Container>
      </div>
    </>
  );
};
export default HomeScreen;
