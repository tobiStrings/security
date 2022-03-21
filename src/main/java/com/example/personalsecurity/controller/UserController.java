package com.example.personalsecurity.controller;

import com.example.personalsecurity.data.dtos.request.LoginRequest;
import com.example.personalsecurity.data.dtos.request.RegisterRequest;
import com.example.personalsecurity.exceptions.SecException;
import com.example.personalsecurity.services.UserService;
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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
        } catch (SecException e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        log.info("loginRequest {}",loginRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.login(loginRequest));
    }
}
