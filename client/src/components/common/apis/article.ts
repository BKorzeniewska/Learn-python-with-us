import { Get } from "../axiosFetch";
import { Result } from "../poliTypes";
import { baseUrl } from "./common";


export type Article = {
    id: number;
    title: string;
    content: string;
    chapterId: number;
    userId: number;
    date: string;
}

export type ArticleResponseError = "ARTICLE_NOT_FOUND"

export const loadArticleById = async (id: string): Promise<Result<Article, ArticleResponseError>> => {
    const response = Get<Article>(`${baseUrl}/api/v1/articles/${id}`);

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<Article, ArticleResponseError>;
        } else {          
            return { isOk: false, error: "ARTICLE_NOT_FOUND" } as Result<Article, ArticleResponseError>;
        }
    });
}