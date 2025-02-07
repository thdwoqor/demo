package com.querypie.service;

import com.querypie.domain.Book;
import com.querypie.domain.BookRepository;
import com.querypie.service.dto.BookSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void save(final BookSaveRequest request) {
        bookRepository.save(new Book(request.title(), request.content()));
    }

}
