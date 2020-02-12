package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.exceptions.TicketAlreadyBookedException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TicketDaoImpl implements TicketDao {

    private static final int MULTIPLE_PRICE_FOR_VIP_SEATS_IN_TIMES = 2;
    private static final double MULTIPLE_PRICE_FOR_RATED_MOVIES_IN_TIMES = 1.2d;

    @Autowired
    private Store store;

    public TicketDaoImpl() {
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        if (auditorium == null) {
            throw new ItemNotFoundException("Auditorium not found by air date time " + dateTime.toString());
        }

        Set<Seat> foundSeats = auditorium.getAllSeats().stream()
                .filter(seat -> seats.contains(seat.getNumber()))
                .collect(Collectors.toSet());

        double result = 0d;
        double basePrice = event.getBasePrice();
        long regularTicketsNumber = auditorium.countRegularSeats(foundSeats);
        long vipTicketsNumber = auditorium.countVipSeats(foundSeats);

        if (vipTicketsNumber != 0 && regularTicketsNumber != 0) {
            result = (basePrice * regularTicketsNumber) + vipTicketsNumber * MULTIPLE_PRICE_FOR_VIP_SEATS_IN_TIMES;
        }
        if (vipTicketsNumber == 0 && regularTicketsNumber != 0) {
            result = basePrice * regularTicketsNumber;
        }

        if (vipTicketsNumber != 0 && regularTicketsNumber == 0) {
            result = basePrice * vipTicketsNumber * MULTIPLE_PRICE_FOR_VIP_SEATS_IN_TIMES;
        }

        if (EventRating.HIGH.equals(event.getRating())) {
            result = result * MULTIPLE_PRICE_FOR_RATED_MOVIES_IN_TIMES;
        }
        return result;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        Optional<Ticket> isAnyTicketBooked = tickets.stream().filter(ticket -> ticket.getSeat().isBooked()).findAny();

        if (isAnyTicketBooked.isPresent()) {
            throw new TicketAlreadyBookedException("Already booked " + isAnyTicketBooked.get().toString());
        }

        tickets.forEach(ticket ->
                store.getTicketMap().put(ticket.toString(), ticket)
        );
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return store.getTicketMap().values().stream()
                .filter(ticket -> ticket.getEvent().getId().equals(event.getId()) && ticket.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }
}
