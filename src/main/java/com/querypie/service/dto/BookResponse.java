package com.querypie.service.dto;

import com.querypie.domain.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        boolean isLoaned
) {

    public static BookResponse from(final Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.isLoaned());
    }
}
