package com.example.learnpython.challenge.model;

import com.example.learnpython.article.Article;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ChallengeResponse {
    private Long id;
    private String question;
    private String name;
    private Type type;
    private String content;
    private List<Long> articlesID;
}
