package com.example.personalsecurity.services;

import com.example.personalsecurity.data.dtos.request.RegisterRequest;
import com.example.personalsecurity.data.models.Role;
import com.example.personalsecurity.data.models.User;
import com.example.personalsecurity.exceptions.SecException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void testSave(){
        User user =  new User();
        user.setFirstName("Ti");
        user.setLastName("Li");
        user.setPassword("pass123");
        user.setEmail("titio@gmail.com");
        user.setPhoneNumber("224452");
        user.getRoles().add(Role.INDIVIDUAL);
        User returnedUser = userService.saveUser(user);
        System.out.println(returnedUser);
        assertThat(returnedUser).isNotNull();
    }

    @Test
    void testRegister() throws SecException {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Ti");
        request.setLastName("Li");
        request.setEmail("titobi@gmail.com");
        request.setPhoneNumber("1234");
//        request.setUserPassword("pass123");
//        String message  = userService.register(request);
//        System.out.println(message);
//        assertThat(message).isNotEmpty();
    }
}