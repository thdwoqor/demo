package com.querypie.service.dto;

import java.time.LocalDate;

public record BookSaveRequest(
        String title,
        String author,
        LocalDate publicationDate
) {
}
