package com.example.personalsecurity.data.dtos.response;

import com.example.personalsecurity.data.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@AllArgsConstructor
@Data
public class ResetPasswordResponse{
    private User user;
    private String message;
}
