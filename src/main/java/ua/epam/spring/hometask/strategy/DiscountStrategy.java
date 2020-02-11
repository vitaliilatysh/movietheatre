package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Seat;

import java.math.BigDecimal;
import java.util.Set;

public interface DiscountStrategy {

    /**
     * @param event    evnet
     * @param seats    number the seats user is buying
     * @param totalSum total price of all buying tickets
     * @return the discount
     */
    BigDecimal count(Event event, Set<Seat> seats, BigDecimal totalSum);
}
