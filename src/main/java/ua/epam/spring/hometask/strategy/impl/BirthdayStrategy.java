package ua.epam.spring.hometask.strategy.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

import java.math.BigDecimal;
import java.util.Set;

public class BirthdayStrategy implements DiscountStrategy {

    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.05);

    public BigDecimal count(Event event, Set<Seat> seats, BigDecimal totalSum) {
        return totalSum.multiply(DISCOUNT);
    }
}
