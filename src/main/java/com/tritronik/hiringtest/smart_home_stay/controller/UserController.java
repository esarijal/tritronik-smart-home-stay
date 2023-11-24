package com.tritronik.hiringtest.smart_home_stay.controller;

import com.tritronik.hiringtest.smart_home_stay.model.enums.Role;
import com.tritronik.hiringtest.smart_home_stay.model.User;
import com.tritronik.hiringtest.smart_home_stay.model.dtos.UserRequest;
import com.tritronik.hiringtest.smart_home_stay.security.AuthenticationService;
import com.tritronik.hiringtest.smart_home_stay.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest registrationRequest) {
        try {
            log.debug(registrationRequest.toString());
            validateRegistrationRequest(registrationRequest);

            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));

            userService.save(user);

            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        String jwtToken = authenticationService.authenticateAndGenerateToken(userRequest);
        return ResponseEntity.ok(jwtToken);
    }

    private void validateRegistrationRequest(UserRequest registrationRequest) throws Exception {
        if (!StringUtils.hasLength(registrationRequest.getUsername()) || !StringUtils.hasLength(registrationRequest.getPassword())) {
            throw new Exception("Username and password are required");
        }
    }
}
