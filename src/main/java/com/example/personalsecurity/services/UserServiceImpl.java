package com.example.personalsecurity.services;

import com.example.personalsecurity.data.dtos.request.LoginRequest;
import com.example.personalsecurity.data.dtos.request.RegisterRequest;
import com.example.personalsecurity.data.dtos.request.ResetPasswordRequest;
import com.example.personalsecurity.data.dtos.request.SetPasswordRequest;
import com.example.personalsecurity.data.dtos.response.JwtAuthenticationResponse;
import com.example.personalsecurity.data.dtos.response.ResetPasswordResponse;
import com.example.personalsecurity.data.dtos.response.SetPasswordResponse;
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

import java.util.Optional;
import java.util.Random;

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
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.getRoles().add(Role.INDIVIDUAL);
        final String API_KEY = "8b57e315c78871227477cb158795eb14-62916a6c-73cd52da";
        final String DoMAIN_NAME ="sandbox144583205df248d490f9255207d5ea30.mailgun.org";
        String text = "Hi "+request.getFirstName()+"\nYour verification number is "+generateVerificationNumber();
        MailGun.sendMail(API_KEY,DoMAIN_NAME,newUser.getEmail(),text);
        saveUser(newUser);
        return "Registration Successful";
    }
    private String generateVerificationNumber(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%04d", number);
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
    @Override
    public SetPasswordResponse setUserPassword(SetPasswordRequest request) throws SecException {
        validateSetPasswordRequest(request);

        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new SecException("Passwords does not match");
        }

        Optional<User> user = userRepository.findByEmail(request.getUserEmail());
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(request.getPassword()));
            user.get().setIsEnabled(true);
            userRepository.save(user.get());
            return new SetPasswordResponse(user.get(),"User succesfully registered");
        }
        throw new SecException("User not found");

    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) throws SecException {
        validateResetPasswordRequest(request);
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()){
            throw new SecException("Invalid email");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())){
            throw new SecException("Invalid old password");
        }

        log.info("Test passed");

        if (passwordEncoder.matches(request.getNewPassword(), user.get().getPassword())){
            throw new SecException("Old password cannot be the same as new password");
        }

        log.info("Test passed again ");
        user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
        User updatedUSer = userRepository.save(user.get());
        return new ResetPasswordResponse(updatedUSer,"Password changed successfully");
    }

    private void validateResetPasswordRequest(ResetPasswordRequest request) throws SecException {
        if (request.getEmail().isEmpty() || request.getEmail().isBlank()){
            throw new SecException("Email cannot be empty");
        }
        if (request.getOldPassword().isBlank() || request.getOldPassword().isEmpty()){
            throw new SecException("Old password cannot be empty");
        }
        if (request.getNewPassword().isEmpty() || request.getNewPassword().isBlank()){
            throw new SecException("New password cannot be empty");
        }
    }

    private void validateSetPasswordRequest(SetPasswordRequest request) throws SecException {
        if (request.getPassword().isBlank() || request.getPassword().isEmpty()){
            throw new SecException("Password Cannot be null");
        }
        if (request.getConfirmPassword().isBlank() || request.getConfirmPassword().isEmpty()){
            throw new SecException("Confirm password cannot be null");
        }
        if (request.getUserEmail().isBlank() || request.getUserEmail().isEmpty()){
            throw new SecException("User's email cannot be null");
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

//        if (request.getPassword().isBlank() || request.getPassword().isEmpty()){
//            throw new SecException("Please enter password");
//        }

    }
}
