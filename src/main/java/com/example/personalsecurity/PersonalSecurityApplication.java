package com.example.personalsecurity;

import com.example.personalsecurity.security.jwt.JWTTokenProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PersonalSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalSecurityApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
    @Bean
    public JWTTokenProvider jwtTokenProvider(){
        return new JWTTokenProvider();
    }
}
