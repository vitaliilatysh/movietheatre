package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.exceptions.TicketAlreadyBookedException;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private TicketDao ticketDao;
    private DiscountService discountService;

    public BookingServiceImpl(TicketDao ticketDao, DiscountService discountService) {
        this.ticketDao = ticketDao;
        this.discountService = discountService;
    }

    @Override
    public BigDecimal getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        int seatsAmount = seats.size();


        if (auditorium == null) {
            throw new ItemNotFoundException("Auditorium not found by air date time " + dateTime.toString());
        }

        Set<Seat> foundSeats = auditorium.getAllSeats().stream()
                .filter(seat -> seats.contains(seat.getNumber()))
                .collect(Collectors.toSet());


        BigDecimal totalPrice = BigDecimal.valueOf(ticketDao.getTicketsPrice(event, dateTime, user, foundSeats));
        BigDecimal discount = discountService.getDiscount(user, event, dateTime, seatsAmount, totalPrice);
        return totalPrice.subtract(discount).setScale(2, BigDecimal.ROUND_CEILING);
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        Optional<Ticket> isAnyTicketBooked = tickets.stream().filter(ticket -> ticket.getSeat().isBooked()).findAny();

        if (isAnyTicketBooked.isPresent()) {
            throw new TicketAlreadyBookedException("Already booked " + isAnyTicketBooked.get().toString());
        }
        ticketDao.bookTickets(tickets);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDao.getPurchasedTicketsForEvent(event, dateTime);
    }
}
