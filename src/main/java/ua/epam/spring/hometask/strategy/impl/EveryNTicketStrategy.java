package ua.epam.spring.hometask.strategy.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

import java.math.BigDecimal;
import java.util.Set;

public class EveryNTicketStrategy implements DiscountStrategy {

    public static final double DISCOUNT = 0.5;
    private static final byte ON_WHAT_TICKET_NUMBER_DISCOUNT_SHOULD_BE_MADE = 10;

    @Override
    public BigDecimal count(Event event, Set<Seat> seats, BigDecimal totalSum) {
        BigDecimal sumSeats = BigDecimal.valueOf(seats.size());
        BigDecimal basePrice = BigDecimal.valueOf(event.getBasePrice());

        BigDecimal discountTicketsAmount = sumSeats.divide(BigDecimal.valueOf(ON_WHAT_TICKET_NUMBER_DISCOUNT_SHOULD_BE_MADE))
                .setScale(0, BigDecimal.ROUND_DOWN);

        BigDecimal totalPriceForNotDiscountTickets = basePrice.multiply(sumSeats.subtract(discountTicketsAmount));
        BigDecimal discountTicketsPrice = basePrice.multiply(discountTicketsAmount).multiply(BigDecimal.valueOf(DISCOUNT));
        BigDecimal totalWithDiscount = totalPriceForNotDiscountTickets.add(discountTicketsPrice);

        if (totalSum.compareTo(totalWithDiscount) > 0) {
            return totalSum.subtract(totalWithDiscount);
        }

        return BigDecimal.ZERO;
    }
}
