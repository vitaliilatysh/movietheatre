package ua.epam.spring.hometask.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.AirDate;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */
public class AirDateMapper implements RowMapper<AirDate> {

    @Override
    public AirDate mapRow(ResultSet resultSet, int i) throws SQLException {
        AirDate airDate = new AirDate();
        airDate.setId(resultSet.getString("id"));
        airDate.setAirDate(resultSet.getTimestamp("airDte").toLocalDateTime());
        return airDate;
    }
}
