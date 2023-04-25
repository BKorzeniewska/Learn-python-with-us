package com.example.learnpython.chapter;

import com.example.learnpython.article.model.MenuArticleResponse;
import com.example.learnpython.chapter.exception.ChapterIllegalStateException;
import com.example.learnpython.chapter.model.ChapterResponse;
import com.example.learnpython.chapter.model.MenuChapterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public ChapterResponse createChapter(String chapterName) {

        if (chapterName.isBlank()) {
            throw new ChapterIllegalStateException(
                    "Chapter name cannot be blank", "CHAPTER_NAME_CANNOT_BE_BLANK", HttpStatus.BAD_REQUEST);
        }
        var chapter = Chapter.builder()
                .name(chapterName)
                .build();

        chapterRepository.save(chapter);
        return chapterMapper.toChapterResponse(chapter);
    }

    @Override
    public List<MenuChapterResponse> getMenuChapters() {
        var menuChapters = chapterRepository.findAll();
        log.info("menuChapters: {}", menuChapters);

        //use mapper
        var menuChapterResponses = menuChapters.stream()
                .filter(Objects::nonNull)
                .map(chapter ->
                        MenuChapterResponse.builder()
                                .id(chapter.getId())
                                .articles(chapter.getArticles().stream()
                                        .map(article -> MenuArticleResponse.builder()
                                            .title(article.getTitle())
                                            .id(article.getId())
                                            .build())
                                        .sorted(Comparator.comparingLong(MenuArticleResponse::getId))
                                        .toList())
                                .name(chapter.getName())
                                .build()).toList();

        return menuChapterResponses;
    }

    @Override
    public List<ChapterResponse> getChapters() {
        var chapters = chapterRepository.findAll();
        return chapters.stream().map(chapterMapper::toChapterResponse).toList();
    }
}
