package com.querypie.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.querypie.DatabaseCleaner;
import com.querypie.domain.BookFixture;
import com.querypie.domain.BookRepository;
import com.querypie.service.dto.BookSaveRequest;
import com.querypie.service.dto.BookSearchCondition;
import com.querypie.service.dto.BookUpdateRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        databaseCleaner.clear();
        Cache localCache = cacheManager.getCache("localCache");
        localCache.clear();
    }

    @Test
    void 디폴트_검색시_캐싱이_적용된다() {
        //given
        given(bookRepository.search(any(), any())).willReturn(List.of(BookFixture.BOOK1));

        //when
        final PageRequest pageRequest = PageRequest.of(0, 10);

        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);

        //then
        verify(bookRepository, times(1)).search(any(), any());
    }

    @Test
    void 검색_조건_추가시_캐싱_되지_않는다() {
        //given
        given(bookRepository.search(any(), any())).willReturn(List.of(BookFixture.BOOK1));

        //when
        final PageRequest pageRequest = PageRequest.of(0, 10);

        bookService.search(new BookSearchCondition("자바", null), pageRequest);
        bookService.search(new BookSearchCondition("자바", null), pageRequest);
        bookService.search(new BookSearchCondition("자바", null), pageRequest);

        //then
        verify(bookRepository, times(3)).search(any(), any());
    }

    @Test
    void 도서_추가시_캐싱이_삭제된다() {
        //given
        given(bookRepository.search(any(), any())).willReturn(List.of(BookFixture.BOOK1));
        given(bookRepository.save(any())).willReturn(BookFixture.BOOK2);

        //when
        final PageRequest pageRequest = PageRequest.of(0, 10);

        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.save(new BookSaveRequest("점프 투 파이썬", "박응용", LocalDate.now()));
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);

        //then
        verify(bookRepository, times(2)).search(any(), any());
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void 도서_수정시_캐싱이_삭제된다() {
        //given
        given(bookRepository.search(any(), any())).willReturn(List.of(BookFixture.BOOK1));
        given(bookRepository.getById(any())).willReturn(BookFixture.BOOK2);

        //when
        final PageRequest pageRequest = PageRequest.of(0, 10);

        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.update(1L, new BookUpdateRequest("도서 수정", "홍길동"));
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);

        //then
        verify(bookRepository, times(2)).search(any(), any());
    }

    @Test
    void 도서_삭제시_캐싱이_삭제된다() {
        //given
        given(bookRepository.search(any(), any())).willReturn(List.of(BookFixture.BOOK1));
        doNothing().when(bookRepository).deleteById(any());

        //when
        final PageRequest pageRequest = PageRequest.of(0, 10);

        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.delete(1L);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);
        bookService.search(new BookSearchCondition(null, null), pageRequest);

        //then
        verify(bookRepository, times(2)).search(any(), any());
    }
}
