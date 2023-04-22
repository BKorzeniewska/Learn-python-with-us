package com.example.learnpython.chapter;

import java.util.List;

public interface ChapterService {
    ChapterResponse createChapter(String chapterName);
    List<ChapterResponse> getChapters();
    List<MenuChapterResponse> getMenuChapters();
}
