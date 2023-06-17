import { baseUrl } from "../../common/apis/common";
import { APIError, Delete, Get, Post } from "../../common/axiosFetch";
import { Result } from "../../common/poliTypes";


export type CommentResponse = {
    id: number;
    content: string;
    createdAt: string;
    articleId: number;
    userDetails: UserDetails;
}

type UserDetails = {
    id: number;
    nickname: string;
    email: string;
    firstname: string;
}

export type CommentResponseError = "COMMENTS_NOT_FOUND";

export const loadCommentsByArticleId = async (id: string): Promise<Result<CommentResponse[], CommentResponseError>> => {
    const response = Get<CommentResponse[]>(`${baseUrl}/api/v1/comment/${id}`);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<CommentResponse[], CommentResponseError>;
        } else {
            return { isOk: false, error: "COMMENTS_NOT_FOUND" } as Result<CommentResponse[], CommentResponseError>;
        }
    });
}

export const createComment = async (articleId: number, content: string): Promise<Result<CommentResponse, APIError>> => {
    const response = Post<CommentResponse, APIError>(`${baseUrl}/api/user/v1/comment/create`, { articleId, content });
  
    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<CommentResponse, APIError>;
      } else {
        return { isOk: false, error: data.error } as Result<CommentResponse, APIError|any>;
        // TODO: Trzeba zrobić podzadek z tymi errorami, fajnie jakby zwracało ten typ APIError bo tam jest spro informacji z serwera
      }
    });
  };
  
  export const deleteComment = async (articleId: number): Promise<Result<any, APIError>> => {
    const response = Delete<any, APIError>(`${baseUrl}/api/admin/v1/comment/delete/${articleId}`);
  
    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<any, APIError>;
      } else {
        return { isOk: false, error: data.error } as Result<any, APIError|any>;
        // TODO: Trzeba zrobić podzadek z tymi errorami, fajnie jakby zwracało ten typ APIError bo tam jest spro informacji z serwera
      }
    });
  };