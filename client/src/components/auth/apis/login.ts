
import { APIError, Post, Put } from "../../common/axiosFetch";
import { Result } from "../../common/poliTypes";
import { baseUrl } from "../../common/apis/common";
import { UserRole } from "../../admin/users/apis/users";

export type authenticateRequest = {
    email: string;
    password: string;
}
export type authenticateResponse = {
    token: string;
    role: UserRole;
    userId: number;
    nickname: string;
    email: string;
}

export type registerRequest = {
    firstname: string;
    nickname: string;
    lastname: string;
    email: string;
    password: string;
}
export type registerResponse = {
    token: string;
}

export type RecoveryRequest = {
    email: string,
    token: string,
    newPassword: string,
}

type RecoveryErrors = any;

export const authenticate = async (email: string, password: string): Promise<Result<authenticateResponse, "UNAUTHORIZED">> => {
    const response = Post<authenticateResponse>(`${baseUrl}/api/v1/auth/authenticate`, { email, password });

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<authenticateResponse, "UNAUTHORIZED">;
        } else {          
            return { isOk: false, error: "UNAUTHORIZED" } as Result<authenticateResponse, "UNAUTHORIZED">;
        }
    });
}

export const register = async (request: registerRequest): Promise<Result<authenticateResponse, "UNAUTHORIZED">> => {
    const response = Post<authenticateResponse>(`${baseUrl}/api/v1/auth/register`, request);

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<authenticateResponse, "UNAUTHORIZED">;
        } else {          
            return { isOk: false, error: "UNAUTHORIZED" } as Result<authenticateResponse, "UNAUTHORIZED">;
        }
    });
}


export const passowrdRecoveryRequest = async (email: string): Promise<Result<any, APIError<RecoveryErrors>>> => {
    const response = Post<any>(`${baseUrl}/api/v1/user/password/reset/request/${email}`, null);

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<any, APIError<RecoveryErrors>>;
        } else {          
            return { isOk: false, error: data.error.response?.data } as Result<any, APIError<RecoveryErrors>>;
        }
    });
}

export const passowrdRecovery = async (req: RecoveryRequest): Promise<Result<any, APIError<RecoveryErrors>>> => {
    const response = Put<any>(`${baseUrl}/api/v1/user/password/reset`, req);

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<any, APIError<RecoveryErrors>>;
        } else {          
            return { isOk: false, error: data.error.response?.data } as Result<any, APIError<RecoveryErrors>>;
        }
    });
}


