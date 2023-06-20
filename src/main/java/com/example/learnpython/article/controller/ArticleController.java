package com.example.learnpython.article.controller;

import com.example.learnpython.article.model.MenuArticleResponse;
import com.example.learnpython.article.service.ArticleService;
import com.example.learnpython.article.model.ArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/article")
@Log4j2
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long articleId) {
        var article = articleService.getArticleById(articleId);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/chapter/{articleId}")
    public ResponseEntity<?> getArticlesByChapter(@PathVariable(name = "articleId") Long articleId) {
        var articles = articleService.getArticlePageByChapter(articleId);
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

    @GetMapping("/newest")
    public ResponseEntity<List<MenuArticleResponse>> getNewestArticle() {
        var articles = articleService.getNewestArticle();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

}
