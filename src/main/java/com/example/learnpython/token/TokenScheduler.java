package com.example.learnpython.token;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TokenScheduler {
    private final TokenRepository tokenRepository;

    //TODO fix deleting query
    //@Scheduled(cron = "0 0 0 */3 * *", zone = "Europe/Warsaw")
    /*@Scheduled(fixedRate = 1000)
    public void deleteExpiredTokens() {
        tokenRepository.deleteAllInvalidTokens();
    }*/
}
