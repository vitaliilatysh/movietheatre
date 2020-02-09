package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;

import java.math.BigDecimal;

public interface DiscountStrategy {

    BigDecimal count(Event event, long seats, long vipSeats, BigDecimal totalSum);
}
