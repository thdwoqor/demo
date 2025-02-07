package com.querypie.controller;

import com.querypie.DatabaseCleaner;
import com.querypie.domain.BookRepository;
import com.querypie.service.dto.BookSaveRequest;
import com.querypie.service.dto.UserSaveRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
                .body(new BookSaveRequest("Java의 정석","자바의 기초부터 실전활용까지 모두 담다!"))
                .when().post("/books")
                .then().log().all()
                .statusCode(200)
                .extract();
    }
}
