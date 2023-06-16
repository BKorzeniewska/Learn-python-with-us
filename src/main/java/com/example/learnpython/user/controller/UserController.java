package com.example.learnpython.user.controller;

import com.example.learnpython.user.model.dto.GetUsersRequest;
import com.example.learnpython.user.model.dto.ModifyUserRequest;
import com.example.learnpython.user.model.dto.UserInfoResponse;
import com.example.learnpython.user.model.dto.UsersDTO;
import com.example.learnpython.user.service.ChangeRoleService;
import com.example.learnpython.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ChangeRoleService changeRoleService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR', 'PRIVILEGED_USER')")
    @PostMapping()
    public ResponseEntity<UsersDTO> getUsers(@RequestBody final GetUsersRequest request) {

        log.info("Get users() - start, request = {}", request);
        final UsersDTO users = changeRoleService.getUsers(request);
        log.info("Get users() - end, users = {}", users);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR', 'PRIVILEGED_USER')")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @RequestHeader(value = "Authorization", required = false) final String token,
            @RequestParam(value = "id", required = false) final Long userId) {

        log.info("Get user info - start");
        return userService.getUserInfo(token, userId);
    }


    @PutMapping("/update")
    public ResponseEntity<UserInfoResponse> modifyUser(
            @RequestHeader(value = "Authorization") final String token,
            @RequestBody final ModifyUserRequest modifyUserRequest) {

        log.info("modifyUser() - start: {}", modifyUserRequest);
        var user = userService.modifyUser(token, modifyUserRequest);
        log.info("modifyUser() - end");
        return user;
    }
}
