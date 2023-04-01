package com.example.learnpython.challenge;


import com.example.learnpython.article.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_ID")
    private long id;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONTENT")
    private String content;

    @ManyToMany(mappedBy = "challenges")
    @JsonIgnore
    private Set<Article> articles;
}
