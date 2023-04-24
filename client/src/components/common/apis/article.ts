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
    nextArticle: number,
    previousArticle: number,
}

export type ArticleShort = {
    id: number;
    title: string;
}

export type ArticleMenu = {
    id: number;
    name: string;
    articles: ArticleShort[];
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

export const loadArticleMenu = async (): Promise<Result<ArticleMenu[], ArticleResponseError>> => {
    const response = Get<ArticleMenu[]>(`${baseUrl}/api/v1/chapters/menu`);

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<ArticleMenu[], ArticleResponseError>;
        } else {          
            return { isOk: false, error: "ARTICLE_NOT_FOUND" } as Result<ArticleMenu[], ArticleResponseError>;
        }
    });
}