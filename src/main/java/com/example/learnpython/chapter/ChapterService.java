package com.example.learnpython.chapter;

import com.example.learnpython.chapter.model.ChapterResponse;
import com.example.learnpython.chapter.model.MenuChapterResponse;

import java.util.List;

public interface ChapterService {
    ChapterResponse createChapter(String chapterName);
    List<ChapterResponse> getChapters();
    List<MenuChapterResponse> getMenuChapters();
}
