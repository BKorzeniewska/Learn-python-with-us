import React, { useState } from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { MarkDownRenderer } from '../common/markdown/MarkDownRenderer';
import SyntaxHighlighter from 'react-syntax-highlighter/dist/esm/default-highlight';
import { CodeProps } from "react-markdown/lib/ast-to-react";
import { oneDark, oneLight } from 'react-syntax-highlighter/dist/cjs/styles/prism';
import { LoadingSpinner } from '../home/Spinner';
import "./challenges.css"; 
import { ExecuteChallengeRequest, executeChallenge } from './apis/challenge';


type ClosedChallengeProps = {
    id: number,
    title: string,
    question: string,
}

export const OpenChallenge = (props: ClosedChallengeProps) => {
    const [isLoading, setIsLoading] = useState(false);
    const [answer, setAnswer] = useState("");

    const handleAnswerChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        setAnswer(event.target.value);
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIsLoading(true);

        const result: ExecuteChallengeRequest = {
            challengeId: props.id,
            answer: answer,
            type: "OPEN",
        }
        executeChallenge(result).then((ans) => {
            setIsLoading(false);
            if(ans.isOk){
                if(ans.value.result === "SUCCESS"){
                    console.log("correct");
                }
                else{
                    console.log("incorrect");
                }
            }
            else{
                console.log("cos sie zjebalo kurwa :/");
            }
        });
    };

    return (
        <Card>
            <Card.Body>
                <Card.Title>{props.title}</Card.Title>
                <Card.Text>
                    <MarkDownRenderer content={props.question} />
                </Card.Text>

                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                        <Form.Label>Write answer below</Form.Label>
                        <Form.Control 
                            as="textarea"
                            style={{ fontFamily: "monospace" }}
                            rows={4}
                            value={answer}
                            onChange={handleAnswerChange}
                        />
                    </Form.Group>
                    <Form.Group>
                        <Button
                            variant="primary"
                            type="submit"
                            className="submit-button"
                            disabled={isLoading}
                        >
                            <LoadingSpinner isLoading={isLoading}>
                                Submit
                            </LoadingSpinner>
                        </Button>
                    </Form.Group>
                    <Form.Group>
                        {/* output */}
                    </Form.Group>
                </Form>
            </Card.Body>
        </Card>
    );
}
