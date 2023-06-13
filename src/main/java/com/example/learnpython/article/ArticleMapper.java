package com.example.learnpython.article;

import com.example.learnpython.article.model.Article;
import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target="chapter.id", source="chapterId")
    Article toArticle(CreateArticleRequest createArticleRequest);

    @Mapping(target="chapterId", source="article.chapter.id")
    @Mapping(target="userId", source="article.user.id")
    @Mapping(target="date", source="article.creationDate")
    ArticleResponse toCreateArticleResponse(Article article);
}
