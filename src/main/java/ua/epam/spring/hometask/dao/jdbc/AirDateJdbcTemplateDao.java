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
import ua.epam.spring.hometask.domain.AirDate;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;

import javax.annotation.Nonnull;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 18.02.2020
 */
@Repository
public class AirDateJdbcTemplateDao implements AirDateDao {

    private static final String SELECT_FROM_AIR_DATES_WHERE_EVENT_ID = "select * from AirDates where event_id = ?";
    private static final String SELECT_FROM_AIR_DATES = "select * from AirDates";
    private static final String DELETE_FROM_EVENTS_WHERE_ID = "delete from Events where id = ?";
    private static final String INSERT_AIR_DATES = "insert into AirDates (airDate, event_id) values (?,?)";
    private static final String SELECT_FROM_AIR_DATES_WHERE_ID = "select * from AirDates where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuditoriumDao auditoriumDao;

    @Autowired
    private EventDao eventDao;

    @Override
    public AirDate save(@Nonnull AirDate object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_AIR_DATES, new String[]{"ID"});
            statement.setDate(1, Date.valueOf(object.getAirDate().toLocalDate()));
            statement.setString(2, object.getEvent().getId());
            return statement;
        }, keyHolder);

        return getById(String.valueOf(keyHolder.getKey()));
    }

    @Override
    public void remove(@Nonnull AirDate object) {
        AirDate foundAirDate = getById(object.getId());
        jdbcTemplate.update(DELETE_FROM_EVENTS_WHERE_ID, foundAirDate.getId());
    }


    @Override
    public AirDate getById(@Nonnull String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_AIR_DATES_WHERE_ID, new Object[]{id}, (resultSet, rowNum) -> {
                AirDate airDate = new AirDate();
                airDate.setId(resultSet.getString("id"));
                airDate.setAirDate(resultSet.getTimestamp("airDte").toLocalDateTime());

                String event_id = resultSet.getString("event_id");
                Event foundEvent = eventDao.getById(event_id);

                airDate.setEvent(foundEvent);

                return airDate;
            });

        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("AirDate not found by id: " + id);
        }
    }

    @Nonnull
    @Override
    public Collection<AirDate> getAll() {
        return jdbcTemplate.query(SELECT_FROM_AIR_DATES, resultSet -> {
            Collection<AirDate> result = new ArrayList<>();
            while (resultSet.next()) {
                AirDate airDate = new AirDate();
                airDate.setId(resultSet.getString("id"));
                airDate.setAirDate(resultSet.getTimestamp("airDte").toLocalDateTime());

                String event_id = resultSet.getString("event_id");
                Event foundEvent = eventDao.getById(event_id);

                airDate.setEvent(foundEvent);
                result.add(airDate);
            }
            return result;
        });
    }

    @Override
    public Collection<AirDate> getAirDatesByEvent(Event event) {
        try {
            return jdbcTemplate.query(SELECT_FROM_AIR_DATES_WHERE_EVENT_ID, new Object[]{event.getId()}, resultSet -> {
                Collection<AirDate> airDates = new ArrayList<>();
                while (resultSet.next()) {
                    AirDate airDate = new AirDate();
                    airDate.setId(resultSet.getString("id"));
                    airDate.setAirDate(resultSet.getTimestamp("airDte").toLocalDateTime());
                    String event_id = resultSet.getString("event_id");
                    Event foundEvent = eventDao.getById(event_id);
                    airDate.setEvent(foundEvent);
                    airDates.add(airDate);
                }
                return airDates;
            });
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("AirDate not found by event id " + event.getId());
        }
    }
}
