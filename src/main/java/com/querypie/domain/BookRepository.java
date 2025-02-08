package com.querypie.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    default Book getById(final Long bookId) {
        return findById(bookId).orElseThrow(() -> new IllegalArgumentException("도서가 존재하지 않습니다."));
    }}
