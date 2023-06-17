package com.example.learnpython.solution.model;


import com.example.learnpython.challenge.Challenge;
import com.example.learnpython.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Builder.Default
    private LocalDateTime attemptedAt = LocalDateTime.now();

    @Column(name = "ANSWER")
    private String answer;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name="CHALLENGE_ID")
    private Challenge challenge;
}
