import { APIError, CheckToken, Delete, Get, Post, Put } from "../../common/axiosFetch";
import { Result } from "../../common/poliTypes";
import { baseUrl } from "../../common/apis/common";


export type Article = {
    id: number;
    title: string;
    content: string;
    chapterId: number;
    userId: number;
    date: string;
    visible: boolean;
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
    title: string;
    content: string;
    chapterId: number;
    visible: boolean;
}

export type ModifyArticleRequest = {
    title: string;
    content: string;
    id: number;
    visible: boolean;
}

export type ChangeVisibilityRequest = {
    articleId: number;
    visible: boolean;
}

export type ArticleErrors = string;

export const loadArticleById = async (id: string): Promise<Result<Article, APIError<ArticleErrors>>> => {
    const response = Get<Article, APIError<ArticleErrors>>(`${baseUrl}/api/v1/article/${id}`);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<Article, APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<Article, APIError<ArticleErrors>>;
        }
    });
}

export const loadArticleMenu = async (): Promise<Result<ArticleMenu[], APIError<ArticleErrors>>> => {
    const response = CheckToken(Get<ArticleMenu[], APIError<ArticleErrors>>(`${baseUrl}/api/v1/chapters/menu`));

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<ArticleMenu[], APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<ArticleMenu[], APIError<ArticleErrors>>;
        }
    });
}

export const loadArticleByIdExtended = async (id: string): Promise<Result<ArticleExtended, APIError<ArticleErrors>>> => {
    const response = Get<ArticleExtended, APIError<ArticleErrors>>(`${baseUrl}/api/v1/article/chapter/${id}`);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<ArticleExtended, APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<ArticleExtended, APIError<ArticleErrors>>;
        }
    });
}

export const loadLatestArticles = async (): Promise<Result<ArticleShort[], APIError<ArticleErrors>>> => {
    const response = Get<ArticleShort[], APIError<ArticleErrors>>(`${baseUrl}/api/v1/article/newest`);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<ArticleShort[], APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<ArticleShort[], APIError<ArticleErrors>>;
        }
    });
}

export const createArticle = async (article: CreateArticleRequest): Promise<Result<Article, APIError<ArticleErrors>>> => {
    const response = Post<Article, APIError<ArticleErrors>>(`${baseUrl}/api/admin/v1/article/create`, article);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<Article, APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<Article, APIError<ArticleErrors>>;
        }
    });
}

export const modifyArticle = async (article: ModifyArticleRequest): Promise<Result<Article, APIError<ArticleErrors>>> => {
    const response = Put<Article, APIError<ArticleErrors>>(`${baseUrl}/api/admin/v1/article/update`, article);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<Article, APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<Article, APIError<ArticleErrors>>;
        }
    });
}

export const deleteArticle = async (articleId: number): Promise<Result<any, APIError<ArticleErrors>>> => {
    const response = Delete<any, APIError<ArticleErrors>>(`${baseUrl}/api/admin/v1/article/delete/${articleId}`);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<any, APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<any, APIError<ArticleErrors>>;
        }
    });
}

export const changeVisibility = async (req: ChangeVisibilityRequest): Promise<Result<any, APIError<ArticleErrors>>> => {
    const response = Put<any, APIError<ArticleErrors>>(`${baseUrl}/api/admin/v1/article/changeVisible`, req);

    return response.then((data) => {
        if (data.isOk) {
            return { isOk: true, value: data.value } as Result<any, APIError<ArticleErrors>>;
        } else {
            return { isOk: false, error: data.error.response?.data } as Result<any, APIError<ArticleErrors>>;
        }
    });
};