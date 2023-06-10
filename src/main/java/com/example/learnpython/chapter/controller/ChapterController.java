package com.example.learnpython.chapter.controller;

import com.example.learnpython.chapter.service.ChapterService;
import com.example.learnpython.chapter.model.ChapterResponse;
import com.example.learnpython.chapter.model.MenuChapterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;

    @GetMapping
    public ResponseEntity<List<ChapterResponse>> getChapters() {
        var chapters = chapterService.getChapters();
        return new ResponseEntity<>(chapters, HttpStatus.OK);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuChapterResponse>> getMenuChapters() {
        var chapters = chapterService.getMenuChapters();
        return new ResponseEntity<>(chapters, HttpStatus.OK);
    }
}
