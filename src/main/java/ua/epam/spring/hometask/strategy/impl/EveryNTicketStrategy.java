package ua.epam.spring.hometask.strategy.impl;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.strategy.DiscountStrategy;
import ua.epam.spring.hometask.strategy.StrategyParams;

import java.math.BigDecimal;

@Component
public class EveryNTicketStrategy implements DiscountStrategy {

    private static final double DISCOUNT = 0.5;
    private static final byte ON_WHAT_TICKET_NUMBER_DISCOUNT_SHOULD_BE_MADE = 10;

    @Override
    public BigDecimal count(StrategyParams params) {
        Event event = params.getEvent();
        BigDecimal totalSum = params.getTotalSum();
        int orderedSeats = params.getOrderedSeats();

        if (orderedSeats >= 10) {
            double basePrice = event.getBasePrice();

            int discountTicketsAmount = orderedSeats / ON_WHAT_TICKET_NUMBER_DISCOUNT_SHOULD_BE_MADE;
            double totalPriceForNotDiscountTickets = basePrice * (orderedSeats - discountTicketsAmount);

            double discountTicketsPrice = basePrice * (discountTicketsAmount * DISCOUNT);
            double totalWithDiscount = totalPriceForNotDiscountTickets + discountTicketsPrice;

            if (totalSum.compareTo(BigDecimal.valueOf(totalWithDiscount)) > 0) {
                return totalSum.subtract(BigDecimal.valueOf(totalWithDiscount));
            }
        }
        return BigDecimal.ZERO;
    }
}
