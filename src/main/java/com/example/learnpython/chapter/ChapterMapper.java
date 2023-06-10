package com.example.learnpython.chapter;

import com.example.learnpython.chapter.model.Chapter;
import com.example.learnpython.chapter.model.ChapterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    ChapterResponse toChapterResponse(Chapter chapter);
}
