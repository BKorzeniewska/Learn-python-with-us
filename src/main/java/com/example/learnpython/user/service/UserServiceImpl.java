package com.example.learnpython.user.service;

import com.example.learnpython.article.exception.ArticleNotFoundException;
import com.example.learnpython.article.repository.ArticleRepository;
import com.example.learnpython.challenge.repository.ChallengeRepository;
import com.example.learnpython.user.model.dto.ModifyUserRequest;
import com.example.learnpython.user.model.entity.User;
import com.example.learnpython.user.repository.UserRepository;
import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.exception.UserRequestException;
import com.example.learnpython.user.model.dto.UserInfoResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ChallengeRepository challengeRepository;

    private final ArticleRepository articleRepository;


    @Override
    public ResponseEntity<UserInfoResponse> getUserInfo(final String token, final Long userId) {

        User user = null;

        if (token == null && userId == null) {
            throw new UserRequestException("Token or userId must be provided", "USER_NOT_FOUND");
        }

        if ((token != null && token.startsWith("Bearer ")) && userId == null) {
            final String jwt = token.substring(7);
            user = userRepository.findByToken(jwt).orElseThrow(() ->
                    new UserNotFoundException("User with provided token not found", "USER_NOT_FOUND"));
        }

        if (userId != null) {
            user = userRepository.findById(userId).orElseThrow(() ->
                    new UserNotFoundException("User with provided id not found", "USER_NOT_FOUND"));
        }

        if (user == null) {
            throw new UserNotFoundException("User with provided token or id not found", "USER_NOT_FOUND");
        }

        final UserInfoResponse userInfoResponse = getUserInfoResponse(user);

        log.info("User info response: {}", userInfoResponse);
        return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
    }

    private static UserInfoResponse getUserInfoResponse(User user) {
        final UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .nickname(user.getNickname())
                .level(user.getLevel())
                .exp(user.getExp())
                .challengesSolvedCount(user.getSolutions().size())
                .build();
        return userInfoResponse;
    }

    @Override
    public ResponseEntity<UserInfoResponse> modifyUser(final String token, final ModifyUserRequest modifyUserRequest) {

        if (token == null) {
            throw new UserRequestException("Token must be provided", "TOKEN_NOT_FOUND");
        }

        final String bearerToken = token.substring(7);
        final User user = userRepository.findByToken(bearerToken)
                .orElseThrow(() -> new ArticleNotFoundException("User with provided token not found", "USER_NOT_FOUND"));
        if (modifyUserRequest.getNickname() != null) {
            user.setNickname(modifyUserRequest.getNickname());
        }

        if (modifyUserRequest.getFirstname() != null) {
            user.setFirstname(modifyUserRequest.getFirstname());
        }

        if (modifyUserRequest.getLastname() != null) {
            user.setLastname(modifyUserRequest.getLastname());
        }

        userRepository.save(user);
        final User result = userRepository.findByToken(bearerToken)
                .orElseThrow(() -> new ArticleNotFoundException("User with provided token not found", "USER_NOT_FOUND"));


        final UserInfoResponse userInfoResponse = getUserInfoResponse(result);

        log.info("User info response: {}", userInfoResponse);
        return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
    }

    @Override
    public void updateUserLevelAndExpById(final long id, final int gainedExp) {

        final User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with provided id not found", "USER_NOT_FOUND"));

        final long exp = calculateExp(user.getExp(), gainedExp);
        final int level = calculateLevel(user.getLevel(), user.getExp(), gainedExp);

        log.info("Updating user level and exp: id={}, level={}, exp={}", id, level, exp);
        userRepository.updateLevelAndExpById(id, level, exp);
    }


    /**
     * Deletes user by email and ALL other related data
     *
     * @param email
     */
    @Override
    public void deleteUserByEmail(final String email) {
        final User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with provided email not found", "USER_NOT_FOUND"));

        int articlesAffected = articleRepository.updateUserToNull(user.getId());
        log.info("Updating {} articles to null", articlesAffected);
        int challengesAffected = challengeRepository.updateUserToNull(user.getId());
        log.info("Updating {} challenges to null", challengesAffected);
        userRepository.deleteByEmail(email);
        log.info("Deleting user by email: {}, user: {}", email, user);
    }

    private int calculateLevel(int currentLevel, long exp, int points) {
        int totalPoints = points + (int) exp;

        if (totalPoints >= 1000) {
            currentLevel += totalPoints / 1000;
        }
        return currentLevel;
    }

    private long calculateExp(final long exp, final int points) {
        long totalPoints = points + exp;

        if (totalPoints >= 1000) {
            totalPoints %= 1000;
        }
        return totalPoints;
    }
}
