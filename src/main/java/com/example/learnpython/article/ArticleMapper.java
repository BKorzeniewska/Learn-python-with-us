package com.example.learnpython.article;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target="chapter.id", source="chapterId")
    @Mapping(target="user.id", source="userId")
    Article toArticle(CreateArticleRequest createArticleRequest);

    @Mapping(target="chapterId", source="article.chapter.id")
    @Mapping(target="userId", source="article.user.id")
    ArticleResponse toCreateArticleResponse(Article article);
}
