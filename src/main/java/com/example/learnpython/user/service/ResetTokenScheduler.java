package com.example.learnpython.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Log4j2
@RequiredArgsConstructor
public class ResetTokenScheduler {

    private final ResetPasswordService resetPasswordService;

    // execute method updateExpiredTokens every hour
    @Scheduled(cron = "0 0 * * * *", zone = "Europe/Warsaw")
    public void deleteExpiredTokens() {
        log.info("deleteExpiredTokens() - scheduler start");
        resetPasswordService.deleteExpiredTokens();
        log.info("deleteExpiredTokens() - scheduler end");
    }

}
