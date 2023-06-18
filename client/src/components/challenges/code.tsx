import React, { useState } from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { MarkDownRenderer } from '../common/markdown/MarkDownRenderer';
import SyntaxHighlighter from 'react-syntax-highlighter/dist/esm/default-highlight';
import { CodeProps } from "react-markdown/lib/ast-to-react";
import { oneDark, oneLight } from 'react-syntax-highlighter/dist/cjs/styles/prism';
import { LoadingSpinner } from '../common/Spinner';
import "./challenges.css"; 
import { ChallengeResult, ExecuteChallengeRequest, executeChallenge } from './apis/challenge';

// implementation of choose component, which is used to choose between 4 answers and has question
type CodeChallengeProps = {
    id: number,
    title: string,
    question: string,
    codeTemplate: string,
    onChallengeComplete: (status: ChallengeResult, answer: string) => void,
}

export const CodeChallenge = (props: CodeChallengeProps) => {
    const lineCount = props.codeTemplate.split("\n").length
    const [isLoading, setIsLoading] = React.useState(false)

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
            type: "CODE",
        }
        executeChallenge(result).then((ans) => {
            setIsLoading(false);
            if(ans.isOk){
                props.onChallengeComplete(ans.value.result, answer);
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

                <Form
                    onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
                        event.preventDefault()
                        handleSubmit(event);
                        // setIsLoading(true)
                        
                        // console.log("submit")
                    }}
                >
                    <Form.Group
                        className="mb-3"
                        controlId="exampleForm.ControlTextarea1"
                    >
                        <Form.Label>Write answer below</Form.Label>
                        <Form.Control 
                            as="textarea"
                            style={{fontFamily: "monospace"}}
                            rows={lineCount+2} 
                            defaultValue={props.codeTemplate}
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
                            <LoadingSpinner
                                isLoading={isLoading}
                            >
                                Submit
                            </LoadingSpinner>
                        </Button>
                    </Form.Group>
                    <Form.Group>
                        {/*output*/}
                        
                    </Form.Group>
                </Form>

            </Card.Body>
        </Card>
    )

}