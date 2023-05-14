package com.example.learnpython.article;

import com.example.learnpython.article.model.ArticleResponse;
import com.example.learnpython.article.model.CreateArticleRequest;
import com.example.learnpython.article.model.ModifyArticleRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/v1/article")
@RequiredArgsConstructor
@Log4j2
public class ArticleAdminController {
    private final ArticleAdminService articleAdminService;

    @PostMapping("/create")
    @Operation(summary = "Create a new article")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody final CreateArticleRequest articleRequest,
                                                         @RequestHeader("Authorization") final String bearerToken) {
        return ResponseEntity.ok(articleAdminService.createArticle(articleRequest, bearerToken));
    }

    @PutMapping("/update")
    public ResponseEntity<?> modifyArticle(@RequestBody final ModifyArticleRequest request) {
        log.info("modifyArticle() - start: {}", request);
        var article = articleAdminService.modifyArticle(request);
        log.info("modifyArticle() - end");
        return new ResponseEntity<>(article, HttpStatus.OK);
    }
}
