package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

@Repository
public class CounterJdbcTemplateDao implements CounterDao {

    private static final String SELECT_FROM_COUNTERS = "select * from Counters";
    private static final String SELECT_TICKETS_BOOKED_FROM_COUNTERS_WHERE_EVENT_ID = "select tickets_booked from Counters where event_id = ?";
    private static final String SELECT_PRICE_CALLED_FROM_COUNTERS_WHERE_EVENT_ID = "select price_called from Counters where event_id = ?";
    private static final String SELECT_NAME_CALLED_FROM_COUNTERS_WHERE_EVENT_ID = "select name_called from Counters where event_id = ?";
    private static final String SELECT_FROM_COUNTERS_WHERE_ID = "select * from Counters where event_id = ?";
    private static final String INSERT_COUNTERS_INTO_COUNTERS = "insert into Counters (name_called, price_called, tickets_booked, event_id) values (?,?,?,?)";
    private static final String UPDATE_COUNTER = "update Counters set %s = ? where event_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EventDao eventDao;

    @Override
    public void remove(@Nonnull Counter object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Counter save(@Nonnull Counter object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_COUNTERS_INTO_COUNTERS, new String[]{"ID"});
            statement.setInt(1, object.getEventCalledByNameCount());
            statement.setInt(2, object.getEventPriceCalledCount());
            statement.setInt(3, object.getEventTicketsBookedCount());
            statement.setString(4, object.getEvent().getId());
            return statement;
        }, keyHolder);

        return getById(String.valueOf(keyHolder.getKey()));
    }

    @Override
    public Counter update(@Nonnull Counter object, String column) {
        jdbcTemplate.update(String.format(UPDATE_COUNTER, column), new Object[]{object.getEventTicketsBookedCount(), object.getEvent().getId()}, new int[]{Types.INTEGER, Types.INTEGER});
        return null;
    }


    @Override
    public Counter getById(@Nonnull String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_COUNTERS_WHERE_ID, new Object[]{id}, (resultSet, rowNum) -> {
                Counter counter = new Counter();
                counter.setId(resultSet.getString("id"));
                counter.setEventCalledByNameCount(resultSet.getInt("name_called"));
                counter.setEventPriceCalledCount(resultSet.getInt("price_called"));
                counter.setEventTicketsBookedCount(resultSet.getInt("tickets_booked"));

                String event_id = resultSet.getString("event_id");
                if (event_id == null) {
                    counter.setEvent(null);
                    return counter;
                }
                Event event = eventDao.getById(event_id);
                counter.setEvent(event);
                return counter;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Counter getByEvent(@Nonnull Event event) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_COUNTERS_WHERE_ID, new Object[]{event.getId()}, (resultSet, rowNum) -> {
                Counter counter = new Counter();
                counter.setId(resultSet.getString("id"));
                counter.setEventCalledByNameCount(resultSet.getInt("name_called"));
                counter.setEventPriceCalledCount(resultSet.getInt("price_called"));
                counter.setEventTicketsBookedCount(resultSet.getInt("tickets_booked"));

                String event_id = resultSet.getString("event_id");
                if (event_id == null) {
                    counter.setEvent(null);
                    return counter;
                }
                Event foundEvent = eventDao.getById(event_id);
                counter.setEvent(foundEvent);
                return counter;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Nonnull
    @Override
    public Collection<Counter> getAll() {
        return jdbcTemplate.query(SELECT_FROM_COUNTERS, resultSet -> {
            Collection<Counter> result = new ArrayList<>();
            while (resultSet.next()) {
                Counter counter = new Counter();
                counter.setId(resultSet.getString("id"));
                counter.setEventCalledByNameCount(resultSet.getInt("name_called"));
                counter.setEventPriceCalledCount(resultSet.getInt("price_called"));
                counter.setEventTicketsBookedCount(resultSet.getInt("tickets_booked"));

                String event_id = resultSet.getString("event_id");
                if (event_id == null) {
                    counter.setEvent(null);
                    continue;
                }
                Event event = eventDao.getById(event_id);
                counter.setEvent(event);

                result.add(counter);
            }
            return result;
        });
    }

    @Override
    public int getCountByEventName(@Nonnull Event event) {
        return jdbcTemplate.queryForObject(SELECT_NAME_CALLED_FROM_COUNTERS_WHERE_EVENT_ID, new Object[]{event.getId()}, Integer.class);
    }

    @Override
    public int getCountByPrice(@Nonnull Event event) {
        return jdbcTemplate.queryForObject(SELECT_PRICE_CALLED_FROM_COUNTERS_WHERE_EVENT_ID, new Object[]{event.getId()}, Integer.class);
    }

    @Override
    public int getCountByTicketBooked(@Nonnull Event event) {
        return jdbcTemplate.queryForObject(SELECT_TICKETS_BOOKED_FROM_COUNTERS_WHERE_EVENT_ID, new Object[]{event.getId()}, Integer.class);
    }
}
