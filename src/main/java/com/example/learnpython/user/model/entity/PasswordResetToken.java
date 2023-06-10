package com.example.learnpython.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
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
    @Column
    private Long id;

    @Column
    private String token;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    @JsonIgnore
    private User user;

    @Column
    private Date expiryDate;

    @Column
    @Builder.Default
    private OffsetDateTime createdDate = OffsetDateTime.now();

    @Column
    @Builder.Default
    private Boolean isExpired = false;

    @Builder.Default
    @Column
    private Boolean used = false;
}