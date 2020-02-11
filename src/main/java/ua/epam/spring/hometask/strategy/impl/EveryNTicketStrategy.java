package ua.epam.spring.hometask.strategy.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

import java.math.BigDecimal;

public class EveryNTicketStrategy implements DiscountStrategy {

    private static final double DISCOUNT = 0.5;
    private static final byte ON_WHAT_TICKET_NUMBER_DISCOUNT_SHOULD_BE_MADE = 10;

    @Override
    public BigDecimal count(Event event, int sumSeats, BigDecimal totalSum) {
        double basePrice = event.getBasePrice();

        int discountTicketsAmount = sumSeats / ON_WHAT_TICKET_NUMBER_DISCOUNT_SHOULD_BE_MADE;
        double totalPriceForNotDiscountTickets = basePrice * (sumSeats - discountTicketsAmount);

        double discountTicketsPrice = basePrice * (discountTicketsAmount * DISCOUNT);
        double totalWithDiscount = totalPriceForNotDiscountTickets + discountTicketsPrice;

        if (totalSum.compareTo(BigDecimal.valueOf(totalWithDiscount)) > 0) {
            return totalSum.subtract(BigDecimal.valueOf(totalWithDiscount));
        }

        return BigDecimal.ZERO;
    }
}
