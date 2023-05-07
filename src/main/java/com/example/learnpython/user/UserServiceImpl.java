package com.example.learnpython.user;

import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.model.UserInfoResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserInfoResponse getUserInfo(final long userId) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with provided id not found", "USER_NOT_FOUND"));

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

        log.info("User info response: {}", userInfoResponse);
        return userInfoResponse;
    }

    @Override
    public void updateUserLevelAndExpById(final long id, final long gainedExp) {

        final User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with provided id not found", "USER_NOT_FOUND"));

        final long exp = user.getExp() + gainedExp;
        final int level = calculateLevel(exp);

        log.info("Updating user level and exp: id={}, level={}, exp={}", id, level, exp);
        userRepository.updateLevelAndExpById(id, level, exp);
    }

    @Override
    @Transactional
    public void deleteUserByEmail(final String email) {

        final User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with provided email not found", "USER_NOT_FOUND"));

        //TODO delete other stuff
        log.info("Deleting user by email: {}, user: {}", email, user);
        userRepository.deleteByEmail(email);
    }

    private int calculateLevel(final long exp) {
        return (int) exp / 1000;
    }
}
