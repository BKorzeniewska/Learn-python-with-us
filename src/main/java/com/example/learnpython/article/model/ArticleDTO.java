package com.example.learnpython.article.model;

import lombok.Data;

@Data
public class ArticleDTO {
    private ArticleResponse article;
    private Long previousArticleIndex;
    private Long nextArticleIndex;

    public ArticleDTO(ArticleResponse resultsPage, Long previous, Long next) {
        this.article = resultsPage;
        this.previousArticleIndex = previous;
        this.nextArticleIndex = next;
    }
}
