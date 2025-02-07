package com.querypie.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.querypie.DatabaseCleaner;
import com.querypie.domain.Book;
import com.querypie.domain.BookRepository;
import com.querypie.service.dto.BookResponse;
import com.querypie.service.dto.BookSaveRequest;
import com.querypie.service.dto.BookUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;
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
    void 책을_등록할_수_있다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new BookSaveRequest("Java의 정석", "남궁성"))
                .when().post("/books")
                .then().log().all()
                .statusCode(200)
                .extract();
    }

    @Test
    void 모든_도서를_조회할_수_있다() {
        //given
        Book book1 = bookRepository.save(new Book("Java의 정석", "남궁성"));
        Book book2 = bookRepository.save(new Book("자바 ORM 표준 JPA 프로그래밍", "김영한"));

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/books")
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        final List<BookResponse> responses = response.jsonPath().getList(".", BookResponse.class);
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).id()).isEqualTo(book1.getId());
        assertThat(responses.get(0).title()).isEqualTo(book1.getTitle());
        assertThat(responses.get(0).author()).isEqualTo(book1.getAuthor());
        assertThat(responses.get(1).id()).isEqualTo(book2.getId());
        assertThat(responses.get(1).title()).isEqualTo(book2.getTitle());
        assertThat(responses.get(1).author()).isEqualTo(book2.getAuthor());
    }

    @Test
    void id로_도서를_조회할_수_있다() {
        //given
        Book book = bookRepository.save(new Book("Java의 정석", "남궁성"));

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/books/" + book.getId())
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        BookResponse userResponse = response.jsonPath().getObject(".", BookResponse.class);
        assertThat(userResponse.id()).isEqualTo(book.getId());
        assertThat(userResponse.title()).isEqualTo(book.getTitle());
        assertThat(userResponse.author()).isEqualTo(book.getAuthor());
    }

    @Test
    void 도서를_수정할_수_있다() {
        //given
        Book book = bookRepository.save(new Book("Java의 정석", "남궁성"));

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new BookUpdateRequest("Java의 정석 2", "송재백"))
                .when().put("/books/" + book.getId())
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        Book updateBook = bookRepository.findById(book.getId()).orElseThrow();
        assertThat(updateBook.getTitle()).isEqualTo("Java의 정석 2");
        assertThat(updateBook.getAuthor()).isEqualTo("송재백");
    }

    @Test
    void 도서를_삭제할_수_있다() {
        //given
        Book book = bookRepository.save(new Book("Java의 정석", "남궁성"));

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete("/books/" + book.getId())
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        assertThat(bookRepository.findById(book.getId())).isEqualTo(Optional.empty());
    }

}
