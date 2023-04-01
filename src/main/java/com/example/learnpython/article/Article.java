package com.example.learnpython.article;

import com.example.learnpython.challenge.Challenge;
import com.example.learnpython.chapter.Chapter;
import com.example.learnpython.comment.Comment;
import com.example.learnpython.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name="CHAPTER_ID")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "ARTICLE_CHALLENGE",
            joinColumns =  @JoinColumn(name = "ARTICLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHALLENGE_ID"))
    private List<Challenge> challenges;
}
