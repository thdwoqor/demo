package com.querypie.service.dto;

public record UserLoginRequest(
        String username,
        String password
) {
}
