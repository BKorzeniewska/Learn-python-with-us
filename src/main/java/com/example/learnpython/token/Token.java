package com.example.learnpython.token;

import com.example.learnpython.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    @Column(name = "TOKEN_ID")
    private Integer id;
    @Column(name = "TOKEN", unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(name = "TOKEN_TYPE")
    private TokenType tokenType = TokenType.BEARER;
    @Column(name = "REVOKED")
    private boolean revoked;
    @Column(name = "EXPIRED")
    private boolean expired;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;
}
