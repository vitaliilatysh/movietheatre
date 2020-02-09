package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;

import java.math.BigDecimal;

public class BirthdayStrategy implements DiscountStrategy {

    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.05);

    public BigDecimal count(Event event, long seats, long vipSeats, BigDecimal totalSum) {
        return totalSum.multiply(DISCOUNT);
    }
}
