import { baseUrl } from "../../common/apis/common";
import { Get } from "../../common/axiosFetch";
import { Result } from "../../common/poliTypes";


export type CommentResponse = {
    id: number;
    content: string;
    createdAt: string;
    articleId: number;
    userId: number;
}

export type CommentResponseError = "COMMENTS_NOT_FOUND"

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