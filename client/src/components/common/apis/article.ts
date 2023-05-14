import { Get, Post } from "../axiosFetch";
import { Result } from "../poliTypes";
import { baseUrl } from "./common";


export type Article = {
    id: number;
    title: string;
    content: string;
    chapterId: number;
    userId: number;
    date: string;
    // nextArticle: number,
    // previousArticle: number,
}

export type ArticleShort = {
    id: number;
    title: string;
}

export type ArticleExtended = {
    article: Article;
    previousArticleIndex: number | null;
    nextArticleIndex: number | null;
    currentArticle: number;
    totalArticles: number;
  };
  

export type ArticleMenu = {
    id: number;
    name: string;
    articles: ArticleShort[];
}

export type CreateArticleRequest = {
    title : string;
    content: string;
    chapterId: number;
    userId: number;
}

export type ArticleResponseError = "ARTICLE_NOT_FOUND"

export const loadArticleById = async (id: string): Promise<Result<Article, ArticleResponseError>> => {
    const response = Get<Article>(`${baseUrl}/api/v1/article/${id}`);

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

export const loadArticleByIdExtended = async (id: string): Promise<Result<ArticleExtended, ArticleResponseError>> => {
    const response = Get<ArticleExtended>(`${baseUrl}/api/v1/article/chapter/${id}`);

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<ArticleExtended, ArticleResponseError>;
        } else {          
            return { isOk: false, error: "ARTICLE_NOT_FOUND" } as Result<ArticleExtended, ArticleResponseError>;
        }
    });
}

export const createArticle  = async (article: CreateArticleRequest): Promise<Result<Article, "UNAUTHORIZED">> => {
    const response = Post<Article>(`${baseUrl}/api/admin/v1/article/create`, { article});

    return response.then((data) => {
        if(data.isOk) {
            return { isOk: true, value: data.value } as Result<Article, "UNAUTHORIZED">;
        } else {          
            return { isOk: false, error: "UNAUTHORIZED" } as Result<Article, "UNAUTHORIZED">;
        }
    });
}