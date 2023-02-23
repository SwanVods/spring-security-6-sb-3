package com.feira.joblist.service;

import com.feira.joblist.model.User;
import com.feira.joblist.repo.UserRepo;
import com.feira.joblist.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo repo;
    private final JwtTokenUtil jwtService;

    public String save(User user) {
        repo.save(user);
        return jwtService.generateToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repo.findByUsername(username);
        return user.map(value -> new org.springframework.security.core.userdetails.User(value.getUsername(), value.getPassword(),
                Collections.emptyList())).orElse(null);

    }
}
