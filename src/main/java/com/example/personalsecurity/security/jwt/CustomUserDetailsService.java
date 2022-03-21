package com.example.personalsecurity.security.jwt;

import com.example.personalsecurity.data.models.User;
import com.example.personalsecurity.data.repository.UserRepository;
import com.example.personalsecurity.security.UserPrincipal;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Let users login with either username or email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email));
        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticatorFilter
    @Transactional
    public UserDetails loadUserById(String id){
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with id : " + id));

        return UserPrincipal.create(user);
    }
}
