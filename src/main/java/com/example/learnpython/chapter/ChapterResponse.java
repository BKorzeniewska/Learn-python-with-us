package com.example.learnpython.chapter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChapterResponse {
    private Long id;
    private String name;
}
