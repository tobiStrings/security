package com.example.personalsecurity.data.dtos.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ForgotPasswordRequest {
    @Email
    private String email;
}
