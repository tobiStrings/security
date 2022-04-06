package com.example.personalsecurity.controller;

import com.example.personalsecurity.data.dtos.request.*;
import com.example.personalsecurity.exceptions.SecException;
import com.example.personalsecurity.services.UserService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
        } catch (SecException | UnirestException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }

    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("loginRequest {}", loginRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.login(loginRequest));
    }

    @PatchMapping("createPassword")
    public ResponseEntity<?> setPassword(@RequestBody SetPasswordRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.setUserPassword(request));
        } catch (SecException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PatchMapping("changePassword")
    public ResponseEntity<?> resetPassword(@RequestBody ChangePasswordRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.changePassword(request));
        } catch (SecException e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PostMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            return ResponseEntity.ok().body(userService.forgotPassword(request));
        } catch (SecException | UnirestException e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PatchMapping("resetPassword/{email}")
    public ResponseEntity<?> resetPassword(@PathVariable String email,@RequestBody ResetPasswordRequest request){
        try {
            return ResponseEntity.ok().body(userService.resetPassword(email,request));
        } catch (SecException e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
}
