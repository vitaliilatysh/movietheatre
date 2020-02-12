package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class BookingServiceImpl implements BookingService {

    private TicketDao ticketDao;
    private DiscountService discountService;

    public BookingServiceImpl(TicketDao ticketDao, DiscountService discountService) {
        this.ticketDao = ticketDao;
        this.discountService = discountService;
    }

    @Override
    public BigDecimal getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        BigDecimal totalPrice = BigDecimal.valueOf(ticketDao.getTicketsPrice(event, dateTime, user, seats));
        BigDecimal discount = discountService.getDiscount(user, event, dateTime, seats.size(), totalPrice);
        return totalPrice.subtract(discount).setScale(2, BigDecimal.ROUND_CEILING);
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        ticketDao.bookTickets(tickets);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDao.getPurchasedTicketsForEvent(event, dateTime);
    }
}
