package com.querypie.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querypie.service.dto.BookSearchCondition;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Book> search(final BookSearchCondition condition) {
        return jpaQueryFactory.selectFrom(QBook.book)
                .where(
                        titleLike(condition.title()),
                        authorLike(condition.author())
                ).fetch();
    }

    private BooleanExpression titleLike(final String title) {
        if (Objects.nonNull(title)) {
            return QBook.book.title.like(title + "%");
        }
        return null;
    }

    private BooleanExpression authorLike(final String author) {
        if (Objects.nonNull(author)) {
            return QBook.book.author.like(author + "%");
        }
        return null;
    }
}
