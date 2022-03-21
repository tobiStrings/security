package com.example.personalsecurity.security;

import com.example.personalsecurity.data.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@EqualsAndHashCode
public class UserPrincipal implements UserDetails{
    private String id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private String phoneNumber;
    private boolean isEnabled;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String id, String name, String username, String email, String password,
                         Boolean isEnabled,String phoneNumber, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = name;
        this.lastName = username;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user){
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new UserPrincipal(user.getId(),user.getFirstName(),user.getLastName(),
        user.getEmail(), user.getPassword(),user.isEnabled(),user.getPhoneNumber(),authorities
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
