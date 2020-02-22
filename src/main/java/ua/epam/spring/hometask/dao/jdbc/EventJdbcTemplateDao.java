package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AirDateDao;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.mappers.EventMapper;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 18.02.2020
 */
@Repository
public class EventJdbcTemplateDao implements EventDao {

    private static final String SELECT_FROM_EVENTS_WHERE_NAME = "select * from Events where name = ?";
    private static final String SELECT_FROM_EVENTS = "select * from Events";
    private static final String DELETE_FROM_EVENTS_WHERE_ID = "delete from Events where id = ?";
    private static final String INSERT_EVENT_INTO_EVENTS = "insert into Events (name, rating, basePrice) values (?,?,?)";
    private static final String SELECT_FROM_EVENTS_WHERE_ID = "select * from Events where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuditoriumDao auditoriumDao;

    @Autowired
    private AirDateDao airDateDao;

    @Override
    public Event getByName(@Nonnull String eventName) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_EVENTS_WHERE_NAME, new Object[]{eventName}, new EventMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Event not found by name " + eventName);
        }
    }

    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Event> getNextEvents(LocalDateTime to) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Event save(@Nonnull Event object) {
        checkEvent(object);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_EVENT_INTO_EVENTS, new String[]{"ID"});
            statement.setString(1, object.getName());
            statement.setString(2, object.getRating().name());
            statement.setDouble(3, object.getBasePrice());
            return statement;
        }, keyHolder);

        return getById(String.valueOf(keyHolder.getKey()));
    }

    @Override
    public void remove(@Nonnull Event object) {
        Event foundEvent = getById(object.getId());
        jdbcTemplate.update(DELETE_FROM_EVENTS_WHERE_ID, foundEvent.getId());
    }

    @Override
    public Event getById(@Nonnull String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_EVENTS_WHERE_ID, new Object[]{id}, new EventMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Event not found by id: " + id);
        }
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return jdbcTemplate.query(SELECT_FROM_EVENTS, new EventMapper());
    }

    private void checkEvent(@Nonnull Event object) {
        String eventId = object.getId();

        if (eventId != null) {
            Event foundEvent = getById(object.getId());

            if (foundEvent != null) {
                throw new ItemAlreadyExistException("Event already registered");
            }
        }
    }
}
