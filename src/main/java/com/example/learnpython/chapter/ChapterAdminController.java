package com.example.learnpython.chapter;

import com.example.learnpython.chapter.model.ChapterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/v1/chapters")
@RequiredArgsConstructor
public class ChapterAdminController {

    private final ChapterService chapterService;

    //@PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping("/create/{chapterName}")
    public ResponseEntity<ChapterResponse> createChapter(@PathVariable("chapterName") String chapterName) {
        var chapter = chapterService.createChapter(chapterName);
        return new ResponseEntity<>(chapter, HttpStatus.CREATED);
    }
}
