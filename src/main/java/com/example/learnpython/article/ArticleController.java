package com.example.learnpython.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long articleId) {
        var article = articleService.getArticleById(articleId);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<ArticleResponse>> getArticlesByChapter(@PathVariable("chapterId") Long chapterId) {
        var articles = articleService.getArticlesByChapter(chapterId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/timestamp/{date}")
    public ResponseEntity<List<ArticleResponse>> getArticleByDate(@PathVariable("date") LocalDate date) {
        var articles = articleService.getArticlesByDate(date);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/title/{titleFragment}")
    public ResponseEntity<List<ArticleResponse>> getArticleByTitleContaining(@PathVariable("titleFragment") String titleFragment) {
        var articles = articleService.getArticlesByTitleContaining(titleFragment);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }


    //TODO add getArticleByDateBetween
}
