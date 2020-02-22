package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;

public interface CounterDao extends BaseDao<Counter> {

    Counter getByEvent(@Nonnull Event event);

    int getCountByEventName(@Nonnull Event event);

    int getCountByPrice(@Nonnull Event event);

    int getCountByTicketBooked(@Nonnull Event event);

    Counter update(@Nonnull Counter counter, String column);
}
