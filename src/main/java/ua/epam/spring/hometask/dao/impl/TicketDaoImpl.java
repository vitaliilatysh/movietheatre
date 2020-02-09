package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketDaoImpl implements TicketDao {

    private Store store;

    public TicketDaoImpl(Store store) {
        this.store = store;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Long> seats) {


        return store.getTicketMap().values().stream()
                .filter(ticket -> ticket.getEvent().getId().equals(event.getId()) &&
                        ticket.getDateTime().equals(dateTime)
                )
                .map(Ticket::getEvent)
                .mapToDouble(Event::getBasePrice)
                .sum();
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
}
