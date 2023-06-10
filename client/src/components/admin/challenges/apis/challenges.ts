import { ChallengeErrors, ChallengeRequest, ChallengeResponse, ChallengeType } from "../../../challenges/apis/challenge";
import { baseUrl } from "../../../common/apis/common";
import { APIError, Post, Put } from "../../../common/axiosFetch";
import { Result } from "../../../common/poliTypes";


export type ModifyChallengeRequest = {
  id: number;
  question: string;
  name: string;
  type: ChallengeType;
  visible: boolean;
  exp: number;
  content: string;
  articlesID: number[];
};

export type CreateChallengeRequest = {
  question: string;
  name: string;
  type: ChallengeType;
  visible: boolean;
  exp: number;
  content: string;
  articlesID: number[];
};

export const createChallenge = async (challenge: CreateChallengeRequest): Promise<Result<ChallengeResponse, APIError<ChallengeErrors>>> => {
  const response = Post<ChallengeResponse, APIError<ChallengeErrors>>(`${baseUrl}/api/admin/v1/challenge/create`, challenge);

  return response.then((data) => {
    if (data.isOk) {
      return { isOk: true, value: data.value } as Result<ChallengeResponse, APIError<ChallengeErrors>>;
    } else {
      return { isOk: false, error: data.error.response?.data } as Result<ChallengeResponse, APIError<ChallengeErrors>>;
    }
  });
};

export const modifyChallenge = async (challenge: ModifyChallengeRequest): Promise<Result<ChallengeResponse, APIError<ChallengeErrors>>> => {
  const response = Put<ChallengeResponse, APIError<ChallengeErrors>>(`${baseUrl}/api/admin/v1/challenge/update`, challenge);

  return response.then((data) => {
    if (data.isOk) {
      return { isOk: true, value: data.value } as Result<ChallengeResponse, APIError<ChallengeErrors>>;
    } else {
      return { isOk: false, error: data.error.response?.data } as Result<ChallengeResponse, APIError<ChallengeErrors>>;
    }
  });
};