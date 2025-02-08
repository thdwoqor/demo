package com.querypie.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    @Embedded
    private LoanStatus loanStatus;

    public Book(final String title, final String author, final LocalDate publicationDate) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public void update(final String title, final String author) {
        this.title = title;
        this.author = author;
    }

    public boolean isLoaned() {
        return loanStatus != null;
    }

    public void loanBook(final User user) {
        if (isLoaned()) {
            throw new IllegalArgumentException("해당 도서는 대출중 입니다.");
        }

        loanStatus = new LoanStatus(
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );
    }

    public void returnBook(final User user) {
        if (!isLoaned()) {
            throw new IllegalArgumentException("해당 도서는 대출중이 아닙니다.");
        }

        if (loanStatus.getUser() != user) {
            throw new IllegalArgumentException("대출자가 아닙니다.");
        }

        loanStatus = null;
    }
}
