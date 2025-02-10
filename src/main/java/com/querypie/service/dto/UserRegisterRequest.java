package com.querypie.service.dto;

import com.querypie.domain.User;

public record UserRegisterRequest(
        String username,
        String password,
        String name
) {

    public User toUser() {
        return new User(username, password, name);
    }
}
