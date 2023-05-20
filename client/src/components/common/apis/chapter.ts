import { Post } from "../axiosFetch";
import { Result } from "../poliTypes";
import { baseUrl } from "./common";


export type ChapterResponse = {
    id: number;
    name: string;
}

export const createChapter = async (name: string): Promise<Result<ChapterResponse, "CHAPTER_NAME_CANNOT_BE_BLANK">> => {
    const response = Post<ChapterResponse>(`${baseUrl}/api/admin/v1/chapters/create/${name}`, null);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<ChapterResponse, "CHAPTER_NAME_CANNOT_BE_BLANK">;
        } else {
            return { isOk: false, error: "CHAPTER_NAME_CANNOT_BE_BLANK" } as Result<ChapterResponse, "CHAPTER_NAME_CANNOT_BE_BLANK">;
        }
    });
}