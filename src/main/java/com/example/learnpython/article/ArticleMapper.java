package com.example.learnpython.article;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toArticle(CreateArticleRequest createArticleRequest);
    CreateArticleResponse toCreateArticleResponse(Article article);
}
