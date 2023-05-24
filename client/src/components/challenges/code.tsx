import React from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { MarkDownRenderer } from '../common/markdown/MarkDownRenderer';
import SyntaxHighlighter from 'react-syntax-highlighter/dist/esm/default-highlight';
import { CodeProps } from "react-markdown/lib/ast-to-react";
import { oneDark, oneLight } from 'react-syntax-highlighter/dist/cjs/styles/prism';
import { LoadingSpinner } from '../home/Spinner';
import "./challenges.css"; 

// implementation of choose component, which is used to choose between 4 answers and has question
type ChooseChallengeProps = {
    title: string,
    question: string,
    codeTemplate: string,
}

export const CodeChallenge = (props: ChooseChallengeProps) => {
    const lineCount = props.codeTemplate.split("\n").length
    const [isLoading, setIsLoading] = React.useState(false)

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
                        setIsLoading(true)
                        console.log("submit")
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