package com.example.learnpython.article;

import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/v1/article")
@RequiredArgsConstructor
public class ArticleAdminController {
    private final ArticleService articleService;

    @PostMapping("/create")
    @Operation(summary = "Create a new article")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody CreateArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.createArticle(articleRequest));
    }
}
