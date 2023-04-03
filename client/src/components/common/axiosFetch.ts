import axios, { AxiosError } from "axios";
import { Result } from "./poliTypes";

export type ErrorType = {
    message: string;
    status: number;
}

export async function Get<T, E = ErrorType>(url: string): Promise<Result<T, E>> {
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
        return { isOk: true, value: data.data } as Result<T, E>;
    }).catch((error: E) => {
        return { isOk: false, error: error } as Result<T, E>;
    });
}

export async function Post<T, E = ErrorType>(url: string, data: any): Promise<Result<T, E>> {
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
        return { isOk: true, value: data.data } as Result<T, E>;
    }).catch((error: E|AxiosError) => {
        return { isOk: false, error: error } as Result<T, E>;
    });
}