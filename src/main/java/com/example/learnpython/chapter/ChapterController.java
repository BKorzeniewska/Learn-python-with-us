package com.example.learnpython.chapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;

    @GetMapping
    public ResponseEntity<List<ChapterResponse>> getChapters() {
        var chapters = chapterService.getChapters();
        return new ResponseEntity<>(chapters, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ChapterResponse> createChapter(@RequestParam String chapterName) {
        var chapter = chapterService.createChapter(chapterName);
        return new ResponseEntity<>(chapter, HttpStatus.CREATED);
    }
}
