package com.example.learnpython.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Log4j2
@RequiredArgsConstructor
public class TokenScheduler {
    private final TokenRepository tokenRepository;

    //Północ co 3 dni
    @Scheduled(cron = "0 0 0 */3 * *", zone = "Europe/Warsaw")
    //@Scheduled(fixedRate = 1000)
    public void deleteExpiredTokens() {
        log.info("Deleting expired tokens");
        tokenRepository.deleteAllInvalidTokens();
    }
}