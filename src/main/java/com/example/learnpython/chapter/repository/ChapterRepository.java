package com.example.learnpython.chapter.repository;


import com.example.learnpython.chapter.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT c FROM Chapter c")
    List<Chapter> findAllChaptersForMenu();
}
