package com.example.learnpython.chapter;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT c FROM Chapter c JOIN Article a ON a.chapter.id = c.id")
    List<Chapter> findAllChaptersForMenu();
}
