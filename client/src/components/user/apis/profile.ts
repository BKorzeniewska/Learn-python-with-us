import { baseUrl } from "../../common/apis/common";
import { Get } from "../../common/axiosFetch";
import { Result } from "../../common/poliTypes";

export type UserInfo = {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    nickname: string;
    level: number;
    exp: number;
    challengesSolvedCount: number;
}

export type UserResponseError = "USER_NOT_FOUND"

export const loadUserById = async (id?: string): Promise<Result<UserInfo, UserResponseError>> => {
    let response: Promise<Result<UserInfo, unknown>>;
    if(id !== undefined) {
    const params = new URLSearchParams({ id });
    response = Get<UserInfo>(`${baseUrl}/api/v1/user/info?${params.toString()}`);
    } else {
       response = Get<UserInfo>(`${baseUrl}/api/v1/user/info`);
    console.log(response);}

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<UserInfo, UserResponseError>;
        } else {          
            return { isOk: false, error: "USER_NOT_FOUND" } as Result<UserInfo, UserResponseError>;
        }
    });
}
