package com.querypie.service.dto;

import com.querypie.domain.Book;
import java.time.LocalDate;

public record BookResponse(
        Long id,
        String title,
        String author,
        LocalDate localDate,
        boolean isLoaned
) {

    public static BookResponse from(final Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationDate(),
                book.isLoaned()
        );
    }
}
