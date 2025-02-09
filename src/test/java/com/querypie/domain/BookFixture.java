package com.querypie.domain;

import java.time.LocalDate;

public class BookFixture {

    public static final Book BOOK1 = new Book("Java의 정석", "남궁성", LocalDate.now());
    public static final Book BOOK2 = new Book("자바 ORM 표준 JPA 프로그래밍", "김영한", LocalDate.now());
}
