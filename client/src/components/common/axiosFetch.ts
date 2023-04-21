import axios, { AxiosError } from "axios";
import { useContext } from "react";
import { AuthContext } from "../auth/AuthContext";
import { Result } from "./poliTypes";

type APIError<T=string> = {
    timestamp: string;
    errorCode: T;
    errorMessage: string;
}

export async function Get<T, E=APIError>(url: string): Promise<Result<T, AxiosError<E>>> {
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

export async function Post<T, E=APIError>(url: string, data: any, token?: string): Promise<Result<T, AxiosError<E>>> {
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
    }).catch((error: E|AxiosError) => {
        return { isOk: false, error: error } as Result<T, AxiosError<E>>;
    });
}
