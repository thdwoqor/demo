package com.querypie.domain;

import com.querypie.service.dto.BookSearchCondition;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookCustomRepository {

    List<Book> search(BookSearchCondition condition, Pageable pageable);
}
