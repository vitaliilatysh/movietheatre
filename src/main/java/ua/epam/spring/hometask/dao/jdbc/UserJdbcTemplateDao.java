package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.mappers.UserMapper;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

@Repository
public class UserJdbcTemplateDao implements UserDao {

    private static final String DELETE_FROM_USERS_WHERE_ID = "delete from Users where id = ?";
    private static final String SELECT_FROM_USERS_WHERE_ID = "select * from Users where id = ?";
    private static final String INSERT_USER_INTO_USERS = "insert into Users (firstName, lastName, email, birthDate) values (?,?,?,?)";
    public static final String SELECT_FROM_USERS_WHERE_EMAIL = "select * from Users where email = ?";
    public static final String SELECT_FROM_USERS = "select * from Users";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByEmail(@Nonnull String userEmail) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_USERS_WHERE_EMAIL, new Object[]{userEmail}, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("User not found by email" + userEmail);
        }
    }

    @Override
    public User save(@Nonnull User object) {
        checkUser(object);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_INTO_USERS, new String[]{"ID"});
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getEmail());
            statement.setDate(4, java.sql.Date.valueOf(object.getBirthDate()));
            return statement;
        }, keyHolder);

        return getById(String.valueOf(keyHolder.getKey()));
    }

    @Override
    public void remove(@Nonnull User object) {
        User foundUser = getById(object.getId());
        jdbcTemplate.update(DELETE_FROM_USERS_WHERE_ID, foundUser.getId());
    }

    @Override
    public User getById(@Nonnull String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_USERS_WHERE_ID, new Object[]{id}, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("User not found by id: " + id);
        }
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(SELECT_FROM_USERS, new UserMapper());
    }

    private void checkUser(@Nonnull User object) {
        String userId = object.getId();

        if (userId != null) {
            User foundUser = getById(object.getId());

            if (foundUser != null) {
                throw new ItemAlreadyExistException("User already registered");
            }
        }
    }
}
