package ua.epam.spring.hometask.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.Auditorium;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

public class AuditoriumMapper implements RowMapper<Auditorium> {

    @Override
    public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
        Auditorium auditorium = new Auditorium();
        auditorium.setId(resultSet.getString("id"));
        auditorium.setName(resultSet.getString("name"));
        return auditorium;
    }
}
