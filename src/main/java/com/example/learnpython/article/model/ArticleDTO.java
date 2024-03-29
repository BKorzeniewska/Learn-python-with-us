package com.example.learnpython.article.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleDTO {
    private ArticleResponse article;
    private Long previousArticleIndex;
    private Long nextArticleIndex;
    private Long currentArticle;
    private Long totalArticles;

    public ArticleDTO(ArticleResponse resultsPage, Long previous, Long next, Long totalArticles, Long currentArticle) {
        this.article = resultsPage;
        this.previousArticleIndex = previous;
        this.nextArticleIndex = next;
        this.totalArticles = totalArticles;
        this.currentArticle = currentArticle;
    }
}
