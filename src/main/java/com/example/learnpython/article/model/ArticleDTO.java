package com.example.learnpython.article.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ArticleDTO {
    private ArticleResponse article;
    private long totalElements;
    private long previousArticleIndex;
    private long nextArticleIndex;
    private int totalPages;
    private int intCurrentPage;
    private boolean isFirst;
    private boolean isLast;

    public ArticleDTO(Page<ArticleResponse> resultsPage, long previous, long next) {
        this.article = resultsPage.getContent().stream().findFirst().orElseThrow();
        this.totalElements = resultsPage.getTotalElements();
        this.totalPages = resultsPage.getTotalPages();
        this.intCurrentPage = resultsPage.getNumber() + 1;
        this.isFirst = resultsPage.isFirst();
        this.isLast = resultsPage.isLast();
        this.previousArticleIndex = previous;
        this.nextArticleIndex = next;
    }
}
