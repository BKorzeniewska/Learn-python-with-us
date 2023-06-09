import axios, { AxiosError } from "axios";
import { useContext } from "react";
import { AuthContext } from "../auth/AuthContext";
import { Result } from "./poliTypes";

export type APIError<T = string> = {
    timestamp: string;
    errorCode: T;
    errorMessage: string;
    httpStatus: string;
}
export async function CheckToken<T>(p: Promise<Result<T, AxiosError<APIError>>>): Promise<Result<T, AxiosError<APIError>>> {
    return p.then((e) => {
        console.log(e);
        if (!e.isOk) {
            if (e.error.response?.data.errorCode === "TOKEN_EXPIRED" ||
                e.error.response?.data.errorCode === "ERROR" ||
                e.error.response?.data.errorCode === "CLAIM_EXCEPTION") {
                // const authContext = useContext(AuthContext);
                // if (authContext) {
                //     authContext.setToken(null);
                // }
                console.log("Token expired");
            }
            return e;
        } else {
            return e;
        }   
    });
}

export async function Get<T, E = APIError>(url: string): Promise<Result<T, AxiosError<E>>> {
    const response = axios.get<T>(url,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });

    return response.then((data) => {
        return { isOk: true, value: data.data } as Result<T, AxiosError<E>>;
    }).catch((error: AxiosError<E>) => {
        return { isOk: false, error: error } as Result<T, AxiosError<E>>;
    });
}

export async function Delete<T, E = APIError>(url: string): Promise<Result<T, AxiosError<E>>> {
    const response = axios.delete<T>(url,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });

    return response.then((data) => {
        return { isOk: true, value: data.data } as Result<T, AxiosError<E>>;
    }).catch((error: AxiosError<E>) => {
        return { isOk: false, error: error } as Result<T, AxiosError<E>>;
    });
}

export async function Post<T, E = APIError>(url: string, data: any, token?: string): Promise<Result<T, AxiosError<E>>> {
    const response = axios.post<T>(url, data,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });

    return response.then((data) => {
        return { isOk: true, value: data.data } as Result<T, AxiosError<E>>;
    }).catch((error: E | AxiosError) => {
        return { isOk: false, error: error } as Result<T, AxiosError<E>>;
    });
}

export async function Put<T, E = APIError>(url: string, data: any, token?: string): Promise<Result<T, AxiosError<E>>> {
    const response = axios.put<T>(url, data,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });

    return response.then((data) => {
        return { isOk: true, value: data.data } as Result<T, AxiosError<E>>;
    }).catch((error: E | AxiosError) => {
        return { isOk: false, error: error } as Result<T, AxiosError<E>>;
    });
}
