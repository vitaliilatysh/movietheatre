package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private DiscountService discountService;

    public BookingServiceImpl() {
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

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForUser(@Nonnull User user) {
        return ticketDao.getPurchasedTicketsForUser(user);
    }
}
