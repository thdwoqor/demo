package com.querypie.controller;

import com.querypie.DatabaseCleaner;
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
class UserControllerTest {

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
    void 사용자를_생성할_수_있다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserSaveRequest("송재백"))
                .when().post("/users")
                .then().log().all()
                .statusCode(200)
                .extract();
    }
}
