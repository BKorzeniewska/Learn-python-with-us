package com.example.learnpython.article;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //TODO zmieniń na POST mapping i dodać @RequestBody to argumentu
    @GetMapping("/create")
    @Operation(summary = "Test endpoint")
    public ResponseEntity<ArticleResponse> createArticle() {
        return ResponseEntity.ok(articleService.createArticle(
                CreateArticleRequest.builder()
                        .title("title")
                        .content("content")
                        .chapterId(1L)
                        .userId(1L)
                        .build()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long articleId) {
        var article = articleService.getArticleById(articleId);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<ArticleResponse>> getArticlesByChapter(@PathVariable("chapterId") Long chapterId) {
        var articles = articleService.getArticlesByChapter(chapterId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}
