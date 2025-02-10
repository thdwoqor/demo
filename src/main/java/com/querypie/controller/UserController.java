package com.querypie.controller;

import com.querypie.service.UserService;
import com.querypie.service.dto.TokenResponse;
import com.querypie.service.dto.UserLoginRequest;
import com.querypie.service.dto.UserRegisterRequest;
import com.querypie.service.dto.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final UserRegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/users/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findById(@PathVariable final Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}
