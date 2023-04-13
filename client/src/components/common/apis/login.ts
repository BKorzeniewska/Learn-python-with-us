import { Post } from "../axiosFetch";
import { Result } from "../poliTypes";

const baseUrl = "http://localhost:8080";

export type authenticateRequest = {
    email: string;
    password: string;
}
export type authenticateResponse = {
    token: string;
}

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


