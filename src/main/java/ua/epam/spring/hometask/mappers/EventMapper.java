package ua.epam.spring.hometask.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */
public class EventMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getString("id"));
        event.setName(resultSet.getString("name"));
        event.setRating(EventRating.valueOf(resultSet.getString("rating")));
        event.setBasePrice(resultSet.getDouble("basePrice"));
        return event;
    }
}
