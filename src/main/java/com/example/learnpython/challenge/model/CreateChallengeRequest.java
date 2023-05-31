package com.example.learnpython.challenge.model;

import lombok.Builder;
import lombok.Data;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
@Builder
public class CreateChallengeRequest {
    private String question;
    private String name;
    private Type type;
    private Boolean visible;
    private String content;
    private List<Long> articlesID;
}
