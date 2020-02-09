package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;

import java.math.BigDecimal;

public class EveryNTicketStrategy implements DiscountStrategy {

    private static final byte N_TICKET_DISCOUNT = 10;

    @Override
    public BigDecimal count(Event event, long seats, long vipSeats, BigDecimal totalSum) {
        BigDecimal sumSeats = BigDecimal.valueOf(seats + vipSeats);
        BigDecimal basePrice = BigDecimal.valueOf(event.getBasePrice());

        BigDecimal winTicketsNumber = sumSeats.divide(BigDecimal.valueOf(N_TICKET_DISCOUNT)).setScale(0, BigDecimal.ROUND_DOWN);

        BigDecimal totalNotWinTicketPrice = basePrice.multiply(sumSeats.subtract(winTicketsNumber));

        BigDecimal winTicketsPrice = basePrice.multiply(winTicketsNumber).multiply(BigDecimal.valueOf(0.5));

        BigDecimal totalWithDiscount = totalNotWinTicketPrice.add(winTicketsPrice);

        if (totalSum.compareTo(totalWithDiscount) > 0) {
            return totalSum.subtract(totalWithDiscount);
        }

        return BigDecimal.ZERO;
    }
}
