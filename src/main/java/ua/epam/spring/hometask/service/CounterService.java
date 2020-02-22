package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;

/**
 * @author Yuriy_Tkach
 */
public interface CounterService extends AbstractDomainObjectService<Counter> {

    int getCountByEventName(@Nonnull Event event);

    int getCountByPrice(@Nonnull Event event);

    int getCountByTicketBooked(@Nonnull Event event);

}
