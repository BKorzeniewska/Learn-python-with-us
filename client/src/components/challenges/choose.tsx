import React, { useEffect, useState } from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { MarkDownRenderer } from '../common/markdown/MarkDownRenderer';
import './challenges.css'
import { PossibleAnswers } from './ChallengeScreen';
import { LoadingSpinner } from '../common/Spinner';
import { ChallengeResult, ExecuteChallengeRequest, addSolution, executeChallenge } from './apis/challenge';
import { type } from 'os';
import { useError } from '../common/ErrorContext';

// implementation of choose component, which is used to choose between 4 answers and has question
type ChooseChallengeProps = {
    id: number,
    title: string,
    question: string,
    possibleAnswers: PossibleAnswers,
    onChallengeComplete: (status: ChallengeResult, answer: string) => void,
}

export const ChooseChallenge = (props: ChooseChallengeProps) => {
    const [selectedAnswer, setSelectedAnswer] = useState<string | null>(null); // Track the selected answer
    const [isAnswerCorrect, setIsAnswerCorrect] = useState(false); // Track if the selected answer is correct
    const [isLoading, setIsLoading] = React.useState(false);
    const { errorMessages, setError } = useError();


    // useEffect( () => {              
    //     answers.sort(() => Math.random() - 0.5)
    // }, [])

    return (
        <Card>
            <Card.Body>
                <Card.Title>{props.title}</Card.Title>
                <Card.Text>
                    <MarkDownRenderer content={props.question} />
                </Card.Text>

                <Form
                    className="mb-3"
                    onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
                        event.preventDefault()
                        if (selectedAnswer === null) {
                            setError("Proszę wybrać odpowiedź.")
                            return;
                        }
                        setIsLoading(true)
                        console.log("submit")
                        const result: ExecuteChallengeRequest = {
                            challengeId: props.id,
                            answer: selectedAnswer,
                            type: "CLOSED",
                        }
                        executeChallenge(result).then((ans) => {
                            setIsLoading(false);
                            if (ans.isOk) {
                                props.onChallengeComplete(ans.value.result, selectedAnswer);
                            }
                            else {
                                console.log("Coś się popsuło nie było mnie słychać");
                            }
                        });
                    }}
                >

                    <Form.Check
                        type="radio"
                        label={props.possibleAnswers.A}
                        name="formHorizontalRadios"
                        onClick={() => setSelectedAnswer("A")}
                    />
                    <Form.Check
                        type="radio"
                        label={props.possibleAnswers.B}
                        name="formHorizontalRadios"
                        onClick={() => setSelectedAnswer("B")}
                    />
                    <Form.Check
                        type="radio"
                        label={props.possibleAnswers.C}
                        name="formHorizontalRadios"
                        onClick={() => setSelectedAnswer("C")}
                    />
                    <Form.Check
                        type="radio"
                        label={props.possibleAnswers.D}
                        name="formHorizontalRadios"
                        onClick={() => setSelectedAnswer("D")}
                    />
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
                </Form>

            </Card.Body>
        </Card>
    )

}