package com.example.learnpython.challenge.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModifyChallengeRequest {
    private Long id;
    private String question;
    private String name;
    private Type type;
    private Boolean visible;
    private Long exp;
    private String content;
    private List<Long> articlesID;
}
