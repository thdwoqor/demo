package com.querypie.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoanStatus {

    @ManyToOne
    private User user;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public LoanStatus(final User user, final LocalDate loanDate, final LocalDate returnDate) {
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
}
