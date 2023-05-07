import React from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { MarkDownRenderer } from '../markdown/MarkDownRenderer';



// implementation of choose component, which is used to choose between 4 answers and has question
type ChooseChallengeProps = {
    title: string,
    question: string,
    answerOk: string,
    answer2: string,
    answer3: string,
    answer4: string
}

export const ChooseChallenge = (props: ChooseChallengeProps) => {
    
    const onRightAnswer = () => {
        console.log("right answer")
    }

    const onWrongAnswer = () => {
        console.log("wrong answer")
    }
    
    var answers = [{q: props.answerOk, a: onRightAnswer}, 
                   {q: props.answer2, a: onWrongAnswer}, 
                   {q: props.answer3, a: onWrongAnswer}, 
                   {q: props.answer4, a: onWrongAnswer}]
                  
    answers.sort(() => Math.random() - 0.5)
    
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
                        />
                    ))}
                </Form>

            </Card.Body>
        </Card>
    )
    
}