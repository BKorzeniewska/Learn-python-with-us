package com.example.learnpython.user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
 
    private static final int EXPIRATION = 60;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String token;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    private Date expiryDate;

    private Boolean used = false;
}