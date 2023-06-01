package com.example.learnpython.article;

import com.example.learnpython.challenge.Challenge;
import com.example.learnpython.chapter.Chapter;
import com.example.learnpython.comment.Comment;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.model.entity.UserHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private long id;

    @Column(name = "TITLE", unique = true)
    private String title;

    @Column(name = "CONTENT", columnDefinition="TEXT")
    private String content;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "VISIBLE")
    private Boolean visible;

    @ManyToOne
    @JoinColumn(name="CHAPTER_ID", nullable = false)
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @OneToMany(mappedBy = "article")
    private List<UserHistory> userHistories;

    @ManyToMany
    @JoinTable(
            name = "ARTICLE_CHALLENGE",
            joinColumns =  @JoinColumn(name = "ARTICLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHALLENGE_ID"))
    private List<Challenge> challenges;
}
