package com.querypie.domain;

import com.querypie.service.dto.BookSearchCondition;
import java.util.List;

public interface BookCustomRepository {

    List<Book> search(BookSearchCondition condition);
}
