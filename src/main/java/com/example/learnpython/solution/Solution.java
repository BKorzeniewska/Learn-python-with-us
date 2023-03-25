package com.example.learnpython.solution;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOLUTION_ID")
    private long id;

    @Column(name = "ATTEMPTED_AT")
    private LocalDateTime attemptedAt;

    @Column(name = "ANSWER")
    private String answer;

    private Long userId;
    private Long challengeId;
}
