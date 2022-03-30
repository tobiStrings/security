package com.example.personalsecurity.services;

import com.example.personalsecurity.data.dtos.request.LoginRequest;
import com.example.personalsecurity.data.dtos.request.RegisterRequest;
import com.example.personalsecurity.data.dtos.response.JwtAuthenticationResponse;
import com.example.personalsecurity.data.models.Role;
import com.example.personalsecurity.data.models.User;
import com.example.personalsecurity.data.repository.UserRepository;
import com.example.personalsecurity.exceptions.SecException;
import com.example.personalsecurity.mail.MailGun;
import com.example.personalsecurity.security.jwt.JWTTokenProvider;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public User saveUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public String register(RegisterRequest request) throws SecException, UnirestException {
        validateRegisterRequest(request);
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.getRoles().add(Role.INDIVIDUAL);
        final String API_KEY = "8b57e315c78871227477cb158795eb14-62916a6c-73cd52da";
        MailGun.sendMail(API_KEY,"",newUser.getEmail());
        saveUser(newUser);
        return "Registration Successful";
    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest request) {
        log.info("Before Authentication");
        try {


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        log.info("Passed the authentication");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        log.info("JWT {}",jwt);
        return new JwtAuthenticationResponse(jwt,"Bearer");
        }catch (Exception e){
            log.error("Error happened");
            log.error("{}", e.getMessage());
            return  null;
        }
    }

    private void validateRegisterRequest(RegisterRequest request) throws SecException {
        if (request.getFirstName().isBlank() || request.getFirstName().isEmpty()){
            throw new SecException("Please enter first name");
        }

        if (request.getLastName().isBlank() || request.getLastName().isEmpty()){
            throw new SecException("Please enter last name");
        }

        if (request.getPhoneNumber().isBlank() || request.getPhoneNumber().isEmpty()){
            throw new SecException("Please enter phone number");
        }

        if (request.getPassword().isBlank() || request.getPassword().isEmpty()){
            throw new SecException("Please enter password");
        }

    }
}
