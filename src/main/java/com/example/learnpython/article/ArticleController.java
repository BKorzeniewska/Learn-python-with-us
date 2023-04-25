package com.example.learnpython.article;

import com.example.learnpython.article.model.ArticleDTO;
import com.example.learnpython.article.model.ArticleResponse;
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

    @GetMapping("/chapter")
    public ResponseEntity<?> getArticlesByChapter(
            @RequestParam(name = "chapterId") Long chapterId,
            @RequestParam(name = "page", required = false) Integer page) {

        //TODO move logic to service
        if (page == null || page < 1) {
            var articles = articleService.getArticlesByChapter(chapterId);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        }
        var articles = articleService.getArticlePageByChapter(chapterId, page);
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

}
