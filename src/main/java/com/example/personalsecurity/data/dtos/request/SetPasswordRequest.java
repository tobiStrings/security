package com.example.personalsecurity.data.dtos.request;

import lombok.Data;

@Data
public class SetPasswordRequest {
    private String password;
    private String confirmPassword;
    private String userEmail;
}
