import { baseUrl } from "../../../common/apis/common";
import { APIError, Get, Post } from "../../../common/axiosFetch";
import { Result } from "../../../common/poliTypes";


export type GetUsersRequest = {
    pageNumber: number,
    query: string,
}
export type User = {
    id: number,
    firstname: string,
    nickname: string,
    lastname: string,
    email: string,
}
export type GetUsersResponse = {
    results: User[];
    totalElements: number;
    totalPages: number;
    intCurrentPage: number;
    isFirst: boolean;
    isLast: boolean;
}

type UsersErrors = any;

export const getUsers = async (req: GetUsersRequest): Promise<Result<GetUsersResponse, APIError<UsersErrors>>> => {
    const response = Post<GetUsersResponse, APIError<UsersErrors>>(`${baseUrl}/api/v1/user`,req);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<GetUsersResponse, APIError<UsersErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<GetUsersResponse, APIError<UsersErrors>>;
        }
    });
};
