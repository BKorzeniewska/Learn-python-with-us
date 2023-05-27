package com.example.learnpython.user.controller;

import com.example.learnpython.user.model.dto.ResetPasswordRequest;
import com.example.learnpython.user.service.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/v1/user/password/reset")
@RequiredArgsConstructor
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping("/request/{email}")
    public void requestResetPassword(@PathVariable("email") final String userEmail) {
        log.info("resetPassword() - start, userEmail = {}", userEmail);
        resetPasswordService.resetPasswordUserRequest(userEmail);
        log.info("resetPassword() - end");
    }

    @PutMapping()
    public void resetPassword(@RequestBody final ResetPasswordRequest resetPasswordRequest) {
        log.info("resetPassword() - start");
        resetPasswordService.resetPassword(resetPasswordRequest);
        log.info("resetPassword() - end");
    }
}
