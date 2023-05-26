package com.example.learnpython.user;

import com.example.learnpython.user.model.ChangeRoleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/admin/v1/user")
public class UserAdminController {
    final ChangeRoleService changeRoleService;

    @PutMapping("/change/role")
    public ResponseEntity<?> changeRole(@RequestBody final ChangeRoleRequest changeRoleRequest,
                                        @RequestHeader("Authorization") final String bearerToken) {
        log.info("changeRole - start");
        changeRoleService.changeRole(changeRoleRequest, bearerToken);
        log.info("changeRole - end");
        return ResponseEntity.ok().build();
    }
}
