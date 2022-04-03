package com.example.personalsecurity.data.dtos.response;

import com.example.personalsecurity.data.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetPasswordResponse{
    private User user;
    private String message;
}
