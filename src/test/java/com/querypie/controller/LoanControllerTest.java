package com.querypie.controller;

import com.querypie.DatabaseCleaner;
import com.querypie.domain.Book;
import com.querypie.domain.BookRepository;
import com.querypie.domain.User;
import com.querypie.domain.UserRepository;
import com.querypie.service.LoanService;
import com.querypie.service.dto.LoanRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoanControllerTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoanService loanService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleaner.clear();
    }

    @Test
    void 도서를_대출할_수_있다() {
        //given
        Book book = bookRepository.save(new Book("Java의 정석", "남궁성"));
        User user = userRepository.save(new User("송재백"));

        //when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoanRequest(book.getId(), user.getId()))
                .when().post("/loans")
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        Book result = bookRepository.getById(book.getId());
        Assertions.assertThat(result.isLoaned()).isEqualTo(true);
    }

    @Test
    void 도서를_반납할_수_있다() {
        //given
        Book book = bookRepository.save(new Book("Java의 정석", "남궁성"));
        User user = userRepository.save(new User("송재백"));

        //when
        loanService.loanBook(new LoanRequest(book.getId(), user.getId()));
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoanRequest(book.getId(), user.getId()))
                .when().delete("/loans")
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        Book result = bookRepository.getById(book.getId());
        Assertions.assertThat(result.isLoaned()).isEqualTo(false);
    }
}
