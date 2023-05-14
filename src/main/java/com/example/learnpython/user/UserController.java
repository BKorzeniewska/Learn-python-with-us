package com.example.learnpython.user;

import com.example.learnpython.user.model.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @RequestHeader(value = "Authorization", required = false) final String token,
            @RequestParam(value = "id", required = false) final Long userId) {

        log.info("Get user info - start");
        return userService.getUserInfo(token, userId);
    }
}
