package ua.epam.spring.hometask.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        user.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
        return user;
    }
}
