package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketDaoImpl implements TicketDao {

    private static final int MULTIPLE_PRICE_FOR_VIP_SEATS_IN_TIMES = 2;
    private static final double MULTIPLE_PRICE_FOR_RATED_MOVIES_IN_TIMES = 1.2d;

    private Store store;

    public TicketDaoImpl(Store store) {
        this.store = store;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Seat> seats) {
        return getPrimarySum(event, seats);
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(ticket ->
                store.getTicketMap().put(ticket.getId(), ticket)
        );
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return store.getTicketMap().values().stream()
                .filter(ticket -> ticket.getEvent().getId().equals(event.getId()) && ticket.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }

    private double getPrimarySum(@Nonnull Event event, Set<Seat> seats) {
        double result = 0d;
        double basePrice = event.getBasePrice();
        long regularTicketsNumber = seats.stream().filter(seat -> seat.getSeatType().equals(SeatType.REGULAR)).count();
        long vipTicketsNumber = seats.stream().filter(seat -> seat.getSeatType().equals(SeatType.VIP)).count();


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
}
