import { useEffect, useState } from "react";
import { CommentResponse, createComment } from "./apis/comment";
import { UserInfo, loadUserById } from "../user/apis/profile";
import { useError } from "../common/ErrorContext";
import { Button, Card, Form } from "react-bootstrap";
import { LoadingSpinner } from "../common/Spinner";

type Props = {
  articleId: number;
  setComments: React.Dispatch<React.SetStateAction<CommentResponse[] | undefined>>;
};

export const CommentForm = (props: Props) => {
  const { errorMessages, setError } = useError();
  const [formContent, setFormContent] = useState("");

  const handleFormSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await createComment(props.articleId, formContent);
      if (response.isOk) {
        props.setComments((prevComments) => [...prevComments || [], response.value]);
        setFormContent("");

      } else {
        setError("Nie udało się dodać komentarza");
      }
    } catch (error) {
      setError("Wystąpił błąd przy dodawaniu komentarza");
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setFormContent(event.target.value);
  };

  return (
    <Card className="mt-4 mb-3">
      <Card.Body>
        <Card.Title>Napisz komentarz</Card.Title>

        <Form onSubmit={handleFormSubmit}>
          <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
            <Form.Label>Twój komentarz</Form.Label>
            <Form.Control
              as="textarea"
              style={{ fontFamily: "monospace" }}
              rows={5}
              value={formContent}
              onChange={handleInputChange}
            />
          </Form.Group>
          <Form.Group>
            <Button variant="primary" type="submit" className="submit-button">
              Submit
            </Button>
          </Form.Group>
        </Form>
      </Card.Body>
    </Card>
  );
};
