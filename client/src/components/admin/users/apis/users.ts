import { ByRoleMatcher } from "@testing-library/react";
import { baseUrl } from "../../../common/apis/common";
import { APIError, Delete, Get, Post, Put } from "../../../common/axiosFetch";
import { Result } from "../../../common/poliTypes";


export type GetUsersRequest = {
    pageNumber: number,
    query: string,
}
export type UserRole  = "USER" |"ADMIN" | "MODERATOR" | "PRIVILEGED_USER";


export const roleRank: Record<UserRole, number> = {
    USER: 1,
    PRIVILEGED_USER: 10,
    MODERATOR: 90,
    ADMIN: 100,
  };

export type User = {
    id: number,
    firstname: string,
    nickname: string,
    lastname: string,
    email: string,
    role: UserRole,
}
export type GetUsersResponse = {
    results: User[];
    totalElements: number;
    totalPages: number;
    intCurrentPage: number;
    isFirst: boolean;
    isLast: boolean;
}

export type ChangeRoleRequest = {
    userId: number,
    role: UserRole,
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

export const changeRole = async (req: ChangeRoleRequest): Promise<Result<null, APIError<UsersErrors>>> => {
    const response = Put<null, APIError<UsersErrors>>(`${baseUrl}/api/admin/v1/user/change/role`,req);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<null, APIError<UsersErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<null, APIError<UsersErrors>>;
        }
    });
};

export const deleteUser = async (email: string): Promise<Result<null, APIError<UsersErrors>>> => {
    const response = Delete<null, APIError<UsersErrors>>(`${baseUrl}/api/admin/v1/user/delete/${email}`);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<null, APIError<UsersErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<null, APIError<UsersErrors>>;
        }
    });
};
