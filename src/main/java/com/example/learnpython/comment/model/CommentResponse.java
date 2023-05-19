package com.example.learnpython.comment.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long articleId;
    private UserDetails userDetails;

    @Data
    @Builder
    public static class UserDetails {
        private Long id;
        private String nickname;
        private String email;
        private String firstname;
    }

}
