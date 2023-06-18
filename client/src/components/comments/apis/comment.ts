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

export type CommentResponseError = any;

export const loadCommentsByArticleId = async (id: string): Promise<Result<CommentResponse[], APIError<CommentResponseError >>> => {
    const response = Get<CommentResponse[], APIError<CommentResponseError >>(`${baseUrl}/api/v1/comment/${id}`);

    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<any, APIError<CommentResponseError >>;
      } else {
        return { isOk: false, error: data.error.response?.data } as Result<any, APIError<CommentResponseError>>;
      }
    });
}

export const createComment = async (articleId: number, content: string): Promise<Result<CommentResponse, APIError<CommentResponseError >>> => {
    const response = Post<CommentResponse, APIError<CommentResponseError >>(`${baseUrl}/api/user/v1/comment/create`, { articleId, content });
  
    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<any, APIError<CommentResponseError >>;
      } else {
        return { isOk: false, error: data.error.response?.data } as Result<any, APIError<CommentResponseError>>;
      }
    });
  };
  
  export const deleteComment = async (articleId: number): Promise<Result<any, APIError<CommentResponseError >>> => {
    const response = Delete<any, APIError<CommentResponseError >>(`${baseUrl}/api/admin/v1/comment/delete/${articleId}`);
  
    return response.then((data) => {
      if (data.isOk) {
        return { isOk: true, value: data.value } as Result<any, APIError<CommentResponseError >>;
      } else {
        return { isOk: false, error: data.error.response?.data } as Result<any, APIError<CommentResponseError>>;
      }
    });
  };