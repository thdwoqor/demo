package com.querypie.controller;

import com.querypie.service.BookService;
import com.querypie.service.dto.BookSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}
