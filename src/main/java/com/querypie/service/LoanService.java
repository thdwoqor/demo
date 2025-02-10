package com.querypie.service;

import com.querypie.domain.Book;
import com.querypie.domain.BookRepository;
import com.querypie.domain.User;
import com.querypie.domain.UserRepository;
import com.querypie.service.dto.LoanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = "localCache", allEntries = true)
    public void loanBook(final LoanRequest request, final Long userId) {
        Book book = bookRepository.getById(request.bookId());
        User user = userRepository.getById(userId);
        book.loanBook(user);
    }

    @CacheEvict(value = "localCache", allEntries = true)
    public void returnBook(final LoanRequest request, final Long userId) {
        Book book = bookRepository.getById(request.bookId());
        User user = userRepository.getById(userId);
        book.returnBook(user);
    }

}
