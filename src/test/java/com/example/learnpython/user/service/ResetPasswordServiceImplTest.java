package com.example.learnpython.user.service;

import com.example.learnpython.mail.EmailSenderMockServiceImpl;
import com.example.learnpython.mail.EmailSenderService;
import com.example.learnpython.user.model.dto.ResetPasswordRequest;
import com.example.learnpython.user.model.entity.PasswordResetToken;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.repository.PasswordResetTokenRepository;
import com.example.learnpython.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
//@ActiveProfiles("test")
class ResetPasswordServiceImplTest {

    @Mock
    private PasswordResetTokenRepository passwordTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ResetPasswordServiceImpl resetPasswordServiceImplUnderTest;

    private static final String email = "email@gmail.com";

    @BeforeEach
    void setUp() {
        final EmailSenderService mailSender = new EmailSenderMockServiceImpl();
        resetPasswordServiceImplUnderTest = new ResetPasswordServiceImpl(
                passwordTokenRepository,
                userRepository,
                mailSender,
                passwordEncoder
        );
    }

    @Test
    public void shouldResetPassword() {
        // given
        final String oldPass = "test";
        final User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(oldPass))
                .build();

        final String token = "1234";

        final PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .build();

        final String newPassword = "new_password";
        final String newPasswordEncoded = passwordEncoder.encode(newPassword);
        final ResetPasswordRequest resetPasswordRequest = ResetPasswordRequest.builder()
                .newPassword(newPassword)
                .email(email)
                .token(token)
                .build();

        //when
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(passwordTokenRepository.findByToken(any())).thenReturn(Optional.of(passwordResetToken));

        //then
        resetPasswordServiceImplUnderTest.resetPassword(resetPasswordRequest);
        user.setPassword(newPasswordEncoded);
        passwordResetToken.setUsed(true);

        assertTrue(passwordEncoder.matches(newPassword, user.getPassword()));
        assertEquals(newPasswordEncoded, user.getPassword());
    }
}