package com.querypie.domain;

import com.querypie.DatabaseCleaner;
import com.querypie.service.dto.BookSearchCondition;
import io.restassured.RestAssured;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest
class BookCustomRepositoryImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        databaseCleaner.clear();
    }

    @Test
    void 도서_제목으로_검색할_수_있다() {
        //given
        Book book1 = bookRepository.save(new Book("Java의 정석", "남궁성"));
        Book book2 = bookRepository.save(new Book("자바 ORM 표준 JPA 프로그래밍", "김영한"));

        //when
        List<Book> books = bookRepository.search(new BookSearchCondition("Java", null));

        //then
        Assertions.assertThat(books.size()).isEqualTo(1);
        Assertions.assertThat(books.getFirst().getTitle()).isEqualTo("Java의 정석");
        Assertions.assertThat(books.getFirst().getAuthor()).isEqualTo("남궁성");
    }

    @Test
    void 도서_저자로_검색할_수_있다() {
        //given
        Book book1 = bookRepository.save(new Book("Java의 정석", "남궁성"));
        Book book2 = bookRepository.save(new Book("자바 ORM 표준 JPA 프로그래밍", "김영한"));

        //when
        List<Book> books = bookRepository.search(new BookSearchCondition(null, "김영한"));

        //then
        Assertions.assertThat(books.size()).isEqualTo(1);
        Assertions.assertThat(books.getFirst().getTitle()).isEqualTo("자바 ORM 표준 JPA 프로그래밍");
        Assertions.assertThat(books.getFirst().getAuthor()).isEqualTo("김영한");
    }
}
