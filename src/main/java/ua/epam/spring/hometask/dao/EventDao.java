package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;

public interface EventDao extends BaseDao<Event> {

    Event getByName(@Nonnull String eventName);

    Collection<Event> getForDateRange(@Nonnull LocalDateTime from, @Nonnull LocalDateTime to);

    Collection<Event> getNextEvents(@Nonnull LocalDateTime to);
}
