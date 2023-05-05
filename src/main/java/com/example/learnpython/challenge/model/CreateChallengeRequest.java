package com.example.learnpython.challenge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateChallengeRequest {
    private String question;
    private String name;
    private Type type;
    private String content;
}
