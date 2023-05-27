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

        final String token = UUID.randomUUID().toString();
        final PasswordResetToken passwordResetToken = createPasswordResetTokenForUser(user, token);

        final ResetPasswordRequest resetPasswordRequest = ResetPasswordRequest.builder()
            .email(userEmail)
            .token(passwordResetToken.getToken())
            .build();

        log.info("resetPasswordUserRequest() - resetPasswordRequest = {}", resetPasswordRequest);
        Runnable sendingEmail = () -> mailSender.sendResetPasswordEmail(resetPasswordRequest);
        new Thread(sendingEmail).start();
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
            throw new ResetTokenNotFound("Token is used or has expired", "INVALID_TOKEN");
        }

        final String newPass = passwordEncoder.encode(resetPasswordRequest.newPassword());

        userRepository.updatePasswordByEmail(resetPasswordRequest.email(), newPass);
        passwordTokenRepository.updateUsedByToken(resetPasswordRequest.token());
        log.info("resetPassword() - end");
    }

    private PasswordResetToken createPasswordResetTokenForUser(final User user, final String token) {
        PasswordResetToken myToken = PasswordResetToken.builder()
                .token(token)
                .expiryDate(calculateExpiryDate())
                .used(false)
                .user(user).build();
        passwordTokenRepository.save(myToken);
        return myToken;
    }

    private Boolean validatePasswordResetToken(final String token, final String email) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResetTokenNotFound("Reset token not found", "TOKEN_NOT_FOUND"));

        final User userRequest = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with provided email not found", "USER_NOT_FOUND"));

        log.info("validatePasswordResetToken() - passToken = {}", passToken);

        if (userRequest.getId().equals(passToken.getUser().getId())) {
            return false;
        }

        log.info("validatePasswordResetToken() - passToken = {}", passToken);

        return !isTokenExpired(passToken);
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
