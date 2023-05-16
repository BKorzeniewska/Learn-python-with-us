import { Get } from "../axiosFetch"
import { Result } from "../poliTypes"
import { baseUrl } from "./common"




type ChallangeResponse = {
    question: string,
    name: string,
    type: string,
    id: number,
    content : {
        correctAnswer: string,
        possibleAnswers: Map<string, string>
    }
}


type Challange = {
    type: "CLOSED",
    id: number,
    title: string,
    question: string,
    correctAnswer: string,
    wrongAnswer1: string,
    wrongAnswer2: string,
    wrongAnswer3: string,
} | {
    type: "OPEN",
    id: number,
    title: string,
    question: string,
    correctAnswer: string,
} | {
    type: "CODE",
    id: number,
    title: string,
    question: string,
    body: string,
}

const mapChallange = (challange: ChallangeResponse): Result<Challange, {}> => {
    switch (challange.type) {
        case "CLOSED":
            return {
                isOk: true,
                value: {
                    type: "CLOSED",
                    id: challange.id,
                    title: challange.name,
                    question: challange.question,
                    correctAnswer: challange.content.correctAnswer,
                    wrongAnswer1: challange.content.possibleAnswers.get("wrongAnswer1")!,
                    wrongAnswer2: challange.content.possibleAnswers.get("wrongAnswer2")!,
                    wrongAnswer3: challange.content.possibleAnswers.get("wrongAnswer3")!,
                }
            }
        case "OPEN":
            return {
                isOk: true,
                value: {
                    type: "OPEN",
                    id: challange.id,
                    title: challange.name,
                    question: challange.question,
                    correctAnswer: challange.content.correctAnswer,
                }
            }
        case "CODE":
            return {
                isOk: true,
                value: {
                    type: "CODE",
                    id: challange.id,
                    title: challange.name,
                    question: challange.question,
                    body: challange.content.correctAnswer,
                }
            }
    }

    return {
        isOk: false,
        error: {}
    }
}

export type GetChallangeResponseError = "CHALLANGE_NOT_FOUND"|"BAD_RESPONSE";

export const getChallenges = async (id: number): Promise<Result<Challange, GetChallangeResponseError>> => {
    const response = Get<ChallangeResponse>(`${baseUrl}/api/v1/challange/${id}`);

    return response.then((data) => {
        if(data.isOk) {
            const challange = mapChallange(data.value);
            if(challange.isOk) {
                return { isOk: true, value: challange.value } as Result<Challange, GetChallangeResponseError>;
            } else {
                return { isOk: false, error: "BAD_RESPONSE" } as Result<Challange, GetChallangeResponseError>;
            }
        } else {          
            return { isOk: false, error: "CHALLANGE_NOT_FOUND" } as Result<Challange, GetChallangeResponseError>;
        }
    });
}


