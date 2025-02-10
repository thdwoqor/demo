package com.querypie.controller;

import com.querypie.service.LoanService;
import com.querypie.service.dto.BookResponse;
import com.querypie.service.dto.LoanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/loans")
    public ResponseEntity<BookResponse> loanBook(
            @RequestBody final LoanRequest request,
            @LoginUserId final Long userId
    ) {
        loanService.loanBook(request, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/loans")
    public ResponseEntity<BookResponse> returnBook(
            @RequestBody final LoanRequest request,
            @LoginUserId final Long userId
    ) {
        loanService.returnBook(request, userId);
        return ResponseEntity.ok().build();
    }
}
