package com.example.learnpython.user.controller;

import com.example.learnpython.user.model.dto.ChangeRoleRequest;
import com.example.learnpython.user.service.ChangeRoleService;
import com.example.learnpython.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/admin/v1/user")
public class UserAdminController {

    private final ChangeRoleService changeRoleService;

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/change/role")
    public ResponseEntity<?> changeRole(@RequestBody final ChangeRoleRequest changeRoleRequest,
                                        @RequestHeader("Authorization") final String bearerToken) {
        log.info("changeRole - start");
        changeRoleService.changeRole(changeRoleRequest, bearerToken);
        log.info("changeRole - end");
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable("email") final String email) {
        log.info("deleteUser - start");
        userService.deleteUserByEmail(email);
        log.info("deleteUser - end");
        return ResponseEntity.ok().build();
    }
}
