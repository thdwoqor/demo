package com.querypie.domain;

import com.querypie.service.dto.UserRegisterRequest;

public class UserFixture {

    public static final UserRegisterRequest USER1_REGISTER_REQUEST = new UserRegisterRequest("test@gmail.com", "test", "송재백");
    public static final User USER1 = new User("test@gmail.com", "test", "송재백");
    public static final User USER2 = new User("test@naver.com", "test", "홍길동");

}
