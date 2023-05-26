package com.example.learnpython.user;

import com.example.learnpython.user.model.GetUsersRequest;
import com.example.learnpython.user.model.UserInfoResponse;
import com.example.learnpython.user.model.UsersDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ChangeRoleService changeRoleService;


    @GetMapping("/dupa")
    public ResponseEntity<UsersDTO> getUsers(@RequestBody final GetUsersRequest request) {

        log.info("Get users() - start, request = {}", request);
        final UsersDTO users = changeRoleService.getUsers(request);
        log.info("Get users() - end, users = {}", users);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @RequestHeader(value = "Authorization", required = false) final String token,
            @RequestParam(value = "id", required = false) final Long userId) {

        log.info("Get user info - start");
        return userService.getUserInfo(token, userId);
    }
}
