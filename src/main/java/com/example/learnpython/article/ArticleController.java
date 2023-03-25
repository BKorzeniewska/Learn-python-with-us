package com.example.learnpython.article;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/create")
    public ResponseEntity<CreateArticleResponse> createArticle() {
        return ResponseEntity.ok(articleService.createArticle(
                CreateArticleRequest.builder()
                        .title("title")
                        .content("content")
                        .chapterId(1L)
                        .userId(1L)
                        .build()
        ));
    }
}
