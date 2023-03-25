package com.example.learnpython.chapter;

import com.example.learnpython.chapter.exception.ChapterIllegalStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public ChapterResponse createChapter(String chapterName) {

        if(chapterName.isBlank()){
            throw new ChapterIllegalStateException("Chapter name cannot be blank");
        }
        var chapter = Chapter.builder()
                .name(chapterName)
                .build();

        chapterRepository.save(chapter);
        return chapterMapper.toChapterResponse(chapter);
    }

    @Override
    public List<ChapterResponse> getChapters() {
        var chapters = chapterRepository.findAll();
        return chapters.stream().map(chapterMapper::toChapterResponse).toList();
    }
}
