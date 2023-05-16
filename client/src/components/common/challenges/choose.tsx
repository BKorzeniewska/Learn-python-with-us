import React, { useEffect, useState } from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { MarkDownRenderer } from '../markdown/MarkDownRenderer';
import './challenges.css'

// implementation of choose component, which is used to choose between 4 answers and has question
type ChooseChallengeProps = {
    title: string,
    question: string,
    answerOk: string,
    answer2: string,
    answer3: string,
    answer4: string,
}

export const ChooseChallenge = (props: ChooseChallengeProps) => {
    const [selectedAnswer, setSelectedAnswer] = useState<number|null>(null); // Track the selected answer
    const [isAnswerCorrect, setIsAnswerCorrect] = useState(false); // Track if the selected answer is correct

    const sendAnswer = (answer: number) => {
        
    }

    const onRightAnswer = (ans: number) => {
        setSelectedAnswer(ans);
        setIsAnswerCorrect(true);
        sendAnswer(ans);
    }

    const onWrongAnswer = (ans: number) => {
        setSelectedAnswer(ans);
        setIsAnswerCorrect(false);
        sendAnswer(ans);
    }
    
    var answers = [{q: props.answerOk, a: () => onRightAnswer(0), id: 0}, 
                   {q: props.answer2, a: () => onWrongAnswer(1), id: 1},
                   {q: props.answer3, a: () => onWrongAnswer(2), id: 2},
                   {q: props.answer4, a: () => onWrongAnswer(3), id: 3}]

    useEffect( () => {              
        answers.sort(() => Math.random() - 0.5)
    }, [])

    return (
        <Card>
            <Card.Body>
                <Card.Title>{props.title}</Card.Title>
                <Card.Text>
                    <MarkDownRenderer content={props.question}/>
                </Card.Text>

                <Form
                    className="mb-3"
                >
                    {answers.map((answer) => (
                        <Form.Check
                            type="radio"
                            label={answer.q}
                            name="formHorizontalRadios"
                            onClick={answer.a}
                            className={
                                selectedAnswer === answer.id
                                    ? isAnswerCorrect
                                        ? 'selected correct'
                                        : 'selected wrong'
                                    : ''
                            }
                        />
                    ))}
                </Form>

            </Card.Body>
        </Card>
    )
    
}