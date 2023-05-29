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
export type ChallengeResponse = {
  id: number;
  question: string;
  name: string;
  type: ChallengeType;
  content: string;
  articlesID: number[];
}

export type ExecuteChallengeRequest = {
  challengeId: number;
  type: ChallengeType;
  answer: string;
}


export type ExecutedChallengeResponse = {
  challengeId: number;
  result: ChallengeResult;
  output: string;

}

type ChallengeErrors = "INTERNAL_SERVER_ERROR" | any;

export const createChallenge = async (challenge: ChallengeRequest): Promise<Result<ChallengeResponse, APIError<ChallengeErrors>>> => {
  const response = Post<ChallengeResponse, APIError<ChallengeErrors>>(`${baseUrl}/api/v1/challenge/create`, challenge);

  return response.then((data) => {
    if (data.isOk) {
      return { isOk: true, value: data.value } as Result<ChallengeResponse, APIError<ChallengeErrors>>;
    } else {
      return { isOk: false, error: data.error.response?.data } as Result<ChallengeResponse, APIError<ChallengeErrors>>;
    }
  });
};

export const executeChallenge = async (challenge: ExecuteChallengeRequest): Promise<Result<ExecutedChallengeResponse, APIError<ChallengeErrors>>> => {
  const response = Post<ExecutedChallengeResponse, APIError<ChallengeErrors>>(`${baseUrl}/api/v1/challenge/execute`, challenge);

  return response.then((data) => {
    if (data.isOk) {
      return { isOk: true, value: data.value } as Result<ExecutedChallengeResponse, APIError<ChallengeErrors>>;
    } else {
      return { isOk: false, error: data.error.response?.data } as Result<ExecutedChallengeResponse, APIError<ChallengeErrors>>;
    }
  });
};

export const getChallengesByArticleId = async (articleId: number): Promise<Result<ChallengeResponse[], APIError<ChallengeErrors>>> => {
  const response = Get<ChallengeResponse[], APIError<ChallengeErrors>>(`${baseUrl}/api/v1/challenge/article/${articleId}`);

  return response.then((data) => {
    if (data.isOk) {
      return { isOk: true, value: data.value } as Result<ChallengeResponse[], APIError<ChallengeErrors>>;
    } else {
      return { isOk: false, error: data.error.response?.data } as Result<ChallengeResponse[], APIError<ChallengeErrors>>;
    }
  });
};

export const getChallengesByName = async (nameFrangment: string): Promise<Result<ChallengeResponse[], APIError<ChallengeErrors>>> => {
  const response = Get<ChallengeResponse[], APIError<ChallengeErrors>>(`${baseUrl}/api/v1/challenge/name/${nameFrangment}`);

  return response.then((data) => {
    if (data.isOk) {
      return { isOk: true, value: data.value } as Result<ChallengeResponse[], APIError<ChallengeErrors>>;
    } else {
      return { isOk: false, error: data.error.response?.data } as Result<ChallengeResponse[], APIError<ChallengeErrors>>;
    }
  });
};

