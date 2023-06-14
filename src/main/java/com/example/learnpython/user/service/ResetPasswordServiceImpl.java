package com.example.learnpython.user.service;

import com.example.learnpython.mail.EmailSenderService;
import com.example.learnpython.user.exception.ResetTokenNotFound;
import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.model.dto.ResetPasswordRequest;
import com.example.learnpython.user.model.entity.PasswordResetToken;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.repository.PasswordResetTokenRepository;
import com.example.learnpython.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Log4j2
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final PasswordResetTokenRepository passwordTokenRepository;
    private final UserRepository userRepository;
    private final EmailSenderService mailSender;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void resetPasswordUserRequest(final String userEmail) {

        final User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User with provided email not found", "USER_NOT_FOUND"));

        log.info("resetPasswordUserRequest() - user = {}", user.getEmail());

        final boolean isPendingPasswordReset = passwordTokenRepository
                .existsByUserIdAndUsedIsFalse(user.getId());

        if (isPendingPasswordReset) {
            log.error("resetPasswordUserRequest() - pending pass reset = {}", user.getEmail());
            throw new ResetTokenNotFound("User already has a pending password reset", "PENDING_PASSWORD_RESET");
        }

        final String token = UUID.randomUUID().toString();
        final PasswordResetToken passwordResetToken = createPasswordResetTokenForUser(user, token);

        final ResetPasswordRequest resetPasswordRequest = ResetPasswordRequest.builder()
            .email(userEmail)
            .token(passwordResetToken.getToken())
            .build();

        log.info("resetPasswordUserRequest() - resetPasswordRequest = {}", resetPasswordRequest);

        mailSender.sendResetPasswordEmail(resetPasswordRequest);
        log.info("resetPasswordUserRequest() - end");
    }

    @Override
    @Transactional
    public void resetPassword(final ResetPasswordRequest resetPasswordRequest) {
        log.info("resetPassword() - start, userEmail = {}", resetPasswordRequest.email());

        final PasswordResetToken passToken = passwordTokenRepository.findByToken(resetPasswordRequest.token())
                .orElseThrow(() -> new ResetTokenNotFound("Reset token not found", "TOKEN_NOT_FOUND"));

        final User userRequest = userRepository.findByEmail(resetPasswordRequest.email())
                .orElseThrow(() -> new UserNotFoundException("User with provided email not found", "USER_NOT_FOUND"));

        if (isTokenExpired(passToken) || passToken.getUsed()) {
            log.error("resetPassword() - token expired or used = {}", passToken);
            throw new ResetTokenNotFound("Token is used or has expired", "INVALID_TOKEN");
        }

        validatePassword(resetPasswordRequest.newPassword(), userRequest.getPassword());
        final String newPass = passwordEncoder.encode(resetPasswordRequest.newPassword());

        userRepository.updatePasswordByEmail(resetPasswordRequest.email(), newPass);
        passwordTokenRepository.updateUsedByToken(resetPasswordRequest.token());
        log.info("resetPassword() - end");
    }

    @Override
    @Transactional
    public void deleteExpiredTokens() {
        log.info("deleteExpiredTokens() - start");
        int deletedTokens = 0;
        try {
            deletedTokens = passwordTokenRepository.deleteAllExpiredSince();
        } catch (Exception e) {
            log.error("deleteExpiredTokens() - error = {}", e.getMessage());
        }
        log.info("deleteExpiredTokens() - deleted rows {} - end", deletedTokens);
    }

    private void validatePassword(final String newPass, final String oldPass) {
        if (passwordEncoder.matches(newPass, oldPass)) {
            log.error("resetPassword() - new password is the same as old password = {}", newPass);
            throw new ResetTokenNotFound("New password is the same as old password", "SAME_PASSWORD");
        }
    }

    private PasswordResetToken createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = PasswordResetToken.builder()
                .token(token)
                .expiryDate(calculateExpiryDate())
                .used(false)
                .user(user).build();
        passwordTokenRepository.save(myToken);
        return myToken;
    }



    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, 60);
        return new Date(cal.getTime().getTime());
    }

}
