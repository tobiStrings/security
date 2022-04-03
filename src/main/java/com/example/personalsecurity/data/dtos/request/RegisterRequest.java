package com.example.personalsecurity.data.dtos.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
//    private String password;
    @Email
    private String email;
    private String phoneNumber;
}
