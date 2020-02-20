package ua.epam.spring.hometask.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

public class TicketMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getString("id"));
        ticket.setUserId(resultSet.getString("user_id"));
        ticket.setAirDateTime(resultSet.getTimestamp("airDate").toLocalDateTime());
        ticket.setEventId(resultSet.getString("event_id"));
        ticket.setSeatId(resultSet.getString("seat_id"));
        return ticket;
    }
}
