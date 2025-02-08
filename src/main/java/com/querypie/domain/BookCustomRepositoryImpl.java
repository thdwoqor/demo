package com.querypie.domain;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querypie.service.dto.BookSearchCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Book> search(
            final BookSearchCondition condition,
            final Pageable pageable
    ) {
        JPAQuery<Book> query = jpaQueryFactory.selectFrom(QBook.book)
                .where(
                        titleLike(condition.title()),
                        authorLike(condition.author())

                )
                .orderBy(
                        getAllOrderSpecifiers(pageable)
                                .stream()
                                .toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return query.fetch();
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            switch (order.getProperty()) {
                case "title":
                    orders.add(new OrderSpecifier<>(direction, QBook.book.title));
                    break;
                case "publicationDate":
                    orders.add(new OrderSpecifier<>(direction, QBook.book.publicationDate));
                    break;
                default:
                    break;
            }
        }
        return orders;
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
