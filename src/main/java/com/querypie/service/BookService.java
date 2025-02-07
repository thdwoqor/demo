package com.querypie.service;

import com.querypie.domain.Book;
import com.querypie.domain.BookRepository;
import com.querypie.service.dto.BookUpdateRequest;
import com.querypie.service.dto.BookResponse;
import com.querypie.service.dto.BookSaveRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void save(final BookSaveRequest request) {
        bookRepository.save(new Book(request.title(), request.author()));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthor()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponse findById(final Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("도서가 존재하지 않습니다."));
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor());
    }

    public void update(final Long id, final BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("도서가 존재하지 않습니다."));
        book.update(request.title(), request.author());
    }

}
