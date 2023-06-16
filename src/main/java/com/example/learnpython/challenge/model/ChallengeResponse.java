package com.example.learnpython.challenge.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChallengeResponse {
    private Long id;
    private String question;
    private String name;
    private Boolean visible;
    private Boolean done;
    private Long userId;
    private Long exp;
    private Type type;
    private String content;
    private List<Long> articlesID;
}
