package com.example.personalsecurity.services;


import com.example.personalsecurity.data.dtos.request.*;
import com.example.personalsecurity.data.dtos.response.*;
import com.example.personalsecurity.data.models.User;
import com.example.personalsecurity.exceptions.SecException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface UserService {
    User saveUser(User newUser);
    String register(RegisterRequest request) throws SecException, UnirestException;
    JwtAuthenticationResponse login(LoginRequest request);
    SetPasswordResponse setUserPassword(SetPasswordRequest request) throws SecException;
    ChangePasswordResponse changePassword(ChangePasswordRequest request) throws SecException;
    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) throws SecException, UnirestException;
    ResetPasswordResponse resetPassword(String email,ResetPasswordRequest request) throws SecException;
}
