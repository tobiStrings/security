package com.example.personalsecurity.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String email;
    private List<Role> roles = new ArrayList<>();
    private boolean isEnabled;

    public void setIsEnabled(boolean v){
        isEnabled = v;
    }
}
