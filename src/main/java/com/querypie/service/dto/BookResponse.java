package com.querypie.service.dto;

public record BookResponse(
        Long id,
        String title,
        String author
) {
}
