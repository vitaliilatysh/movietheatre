package ua.epam.spring.hometask.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.domain.SeatType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

public class SeatMapper implements RowMapper<Seat> {

    @Override
    public Seat mapRow(ResultSet resultSet, int i) throws SQLException {
        Seat seat = new Seat();
        seat.setNumber(resultSet.getLong("id"));
        seat.setSeatType(SeatType.valueOf(resultSet.getString("type")));
        seat.setAuditoriumId(resultSet.getInt("auditorium_id"));
        return seat;
    }
}
