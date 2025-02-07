package com.querypie.controller;

import com.querypie.service.BookService;
import com.querypie.service.dto.BookUpdateRequest;
import com.querypie.service.dto.BookResponse;
import com.querypie.service.dto.BookSaveRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<Void> save(@RequestBody final BookSaveRequest request) {
        bookService.save(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> findById(@PathVariable final Long bookId) {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> update(
            @PathVariable final Long bookId,
            @RequestBody final BookUpdateRequest request
    ) {
        bookService.update(bookId,request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> delete(
            @PathVariable final Long bookId
    ) {
        bookService.delete(bookId);
        return ResponseEntity.ok().build();
    }

}
