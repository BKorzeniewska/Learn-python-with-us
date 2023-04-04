import axios, { AxiosError } from "axios";
import { Result } from "./poliTypes";

export async function Get<T, E>(url: string): Promise<Result<T, AxiosError<E>>> {
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

export async function Post<T, E>(url: string, data: any): Promise<Result<T, AxiosError<E>>> {
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