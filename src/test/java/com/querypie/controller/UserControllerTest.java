package com.querypie.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.querypie.DatabaseCleaner;
import com.querypie.domain.User;
import com.querypie.domain.UserRepository;
import com.querypie.service.dto.UserResponse;
import com.querypie.service.dto.UserSaveRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
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
    @Autowired
    private UserRepository userRepository;

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

    @Test
    void 모든_사용자를_조회할_수_있다() {
        //given
        User user1 = userRepository.save(new User("송재백"));
        User user2 = userRepository.save(new User("홍길동"));

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/users")
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        final List<UserResponse> responses = response.jsonPath().getList(".", UserResponse.class);
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).id()).isEqualTo(user1.getId());
        assertThat(responses.get(0).name()).isEqualTo(user1.getName());
        assertThat(responses.get(1).id()).isEqualTo(user2.getId());
        assertThat(responses.get(1).name()).isEqualTo(user2.getName());
    }

    @Test
    void id로_사용자를_조회할_수_있다() {
        //given
        User user = userRepository.save(new User("송재백"));

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/users/"+user.getId())
                .then().log().all()
                .statusCode(200)
                .extract();

        //than
        UserResponse userResponse = response.jsonPath().getObject(".", UserResponse.class);
        assertThat(userResponse.id()).isEqualTo(user.getId());
        assertThat(userResponse.name()).isEqualTo(user.getName());
    }
}
