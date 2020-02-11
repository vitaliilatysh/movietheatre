package ua.epam.spring.hometask.strategy.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

import java.math.BigDecimal;

public class BirthdayStrategy implements DiscountStrategy {

    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.05);

    public BigDecimal count(Event event, int seats, BigDecimal totalSum) {
        return totalSum.multiply(DISCOUNT);
    }
}
