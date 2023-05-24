import { string } from "prop-types";
import { baseUrl } from "../../common/apis/common";
import { APIError, Get, Post } from "../../common/axiosFetch";
import { Result } from "../../common/poliTypes";

export type ChallengeType = "OPEN" | "CLOSED" | "CODE";
export type ChallengeResult = "SUCCESS" | "FAIL";

export type ChallengeRequest = {
    question: string;
    name: string;
    type: ChallengeType;
    content: string;
    articlesID: number[];
}
export type ChallengeResponse = ChallengeRequest;

export type ExecuteChallengeRequest ={
    challengeId: number;
    type: ChallengeType;
    answer: string;
}


export type ExecutedChallengeResponse = {
    challengeId: number;
    result: ChallengeResult;
    output: string;

}


export const createChallenge = async (challenge: ChallengeRequest): Promise<Result<ChallengeResponse, APIError>> => {
    const response = Post<ChallengeResponse, APIError>(`${baseUrl}/api/v1/challenge/create`, challenge);
  
    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<ChallengeResponse, APIError>;
      } else {
        return { isOk: false, error: data.error } as Result<ChallengeResponse, APIError|any>;
        // TODO: Trzeba zrobić podzadek z tymi errorami, fajnie jakby zwracało ten typ APIError bo tam jest spro informacji z serwera
      }
    });
  };
  
  export const executeChallenge = async (challenge: ExecuteChallengeRequest): Promise<Result<ExecutedChallengeResponse, APIError>> => {
    const response = Post<ExecutedChallengeResponse, APIError>(`${baseUrl}/api/v1/challenge/execute`, challenge);
  
    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<ExecutedChallengeResponse, APIError>;
      } else {
        return { isOk: false, error: data.error } as Result<ExecutedChallengeResponse, APIError|any>;
        // TODO: Trzeba zrobić podzadek z tymi errorami, fajnie jakby zwracało ten typ APIError bo tam jest spro informacji z serwera
      }
    });
  };

  export const getChallengesByArticleId = async (articleId: number): Promise<Result<ChallengeResponse[], APIError>> => {
    const response = Get<ChallengeResponse[], APIError>(`${baseUrl}/api/v1/challenge/article/${articleId}`);
  
    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<ChallengeResponse[], APIError>;
      } else {
        return { isOk: false, error: data.error } as Result<ChallengeResponse[], APIError|any>;
        // TODO: Trzeba zrobić podzadek z tymi errorami, fajnie jakby zwracało ten typ APIError bo tam jest spro informacji z serwera
      }
    });
  };
  
