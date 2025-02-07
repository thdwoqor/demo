package com.querypie.controller;

import com.querypie.service.UserService;
import com.querypie.service.dto.UserSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> save(@RequestBody final UserSaveRequest request){
        userService.save(request);
        return ResponseEntity.ok().build();
    }
}
