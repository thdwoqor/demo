package com.querypie.service.dto;

public record BookUpdateRequest(
        String title,
        String author
) {
}
