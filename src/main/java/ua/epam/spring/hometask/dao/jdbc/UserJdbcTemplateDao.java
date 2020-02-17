package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.mapper.UserMapper;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

@Repository
public class UserJdbcTemplateDao implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByEmail(@Nonnull String userEmail) {
        return null;
    }

    @Override
    public User save(@Nonnull User object) {
        jdbcTemplate.update("insert into User (firstName, lastName, email, birthDate) values (?,?,?,?)",
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                object.getBirthDate());

        return null;
    }

    @Override
    public void remove(@Nonnull User object) {

    }

    @Override
    public User getById(@Nonnull String id) {
        return null;
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("select * from User", new UserMapper());
    }
}
