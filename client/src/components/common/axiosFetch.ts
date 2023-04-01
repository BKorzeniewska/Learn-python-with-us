import axios from "axios";


export async function Get<T>(url: string): Promise<T> {
    const data = await axios.get<T>(url,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });
    return data.data;
}

export async function Post<T>(url: string, payload: string): Promise<T> {
    const data = await axios.post(url, payload,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });
    return data.data;
}

export async function Delete<T>(url: string): Promise<T> {
    const data = await axios.delete(url,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });

    return data.data;
}


export async function Put<T>(url: string, payload: string): Promise<T> {
    const data = await axios.put(url, payload,
        {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: (status) => {
                return status < 300 && status >= 200;
            }
        });

    return data.data;
}


