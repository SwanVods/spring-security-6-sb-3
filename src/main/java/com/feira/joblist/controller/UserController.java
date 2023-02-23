package com.feira.joblist.controller;

import com.feira.joblist.model.User;
import com.feira.joblist.model.dto.UserDto;
import com.feira.joblist.service.UserService;
import com.feira.joblist.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userService.loadUserByUsername(userDto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        String token = userService.save(user);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userdto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userdto.getUsername(),
                            userdto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
        User user = new User();
        UserDetails userDetails = userService.loadUserByUsername(userdto.getUsername());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(token);
    }
}
