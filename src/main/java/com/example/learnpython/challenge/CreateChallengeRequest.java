package com.example.learnpython.challenge;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateChallengeRequest {
    private String question;
    private String name;
    private String content;
}
