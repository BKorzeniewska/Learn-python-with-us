package com.example.learnpython.challenge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisibleChangeRequest {
    private Long challengeId;
    private Boolean visible;
}
