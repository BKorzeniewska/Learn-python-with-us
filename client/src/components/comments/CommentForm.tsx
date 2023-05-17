import { useEffect, useState } from "react";
import { CommentResponse } from "./apis/comment"
import { UserInfo, loadUserById } from "../user/apis/profile";
import { useError } from "../home/ErrorContext";
import { Button, Card, Form } from "react-bootstrap";
import { LoadingSpinner } from "../home/Spinner";


type Props = {
    articleId: number;
}

export const CommentForm = (props: Props) => {
    const { errorMessages, setError } = useError();

  
    return (
        <Card className="mt-4">
        <Card.Body>
            <Card.Title>Napisz komentarz</Card.Title>

            <Form
                onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
                    event.preventDefault()
                    // setIsLoading(true)
                    console.log("submit add comment")
                    setError("Backend nie działa XD")
                }}
            >
                <Form.Group
                    className="mb-3"
                    controlId="exampleForm.ControlTextarea1"
                >
                    <Form.Label>Twój komentarz</Form.Label>
                    <Form.Control 
                        as="textarea"
                        style={{fontFamily: "monospace"}}
                        rows={5} 
                        defaultValue={""}

                        />
                </Form.Group>
                <Form.Group>
                    <Button
                        variant="primary"
                        type="submit"
                        className="submit-button"
                        >
                            Submit
                    </Button>
                </Form.Group>
            </Form>

        </Card.Body>
    </Card>
    );
  };