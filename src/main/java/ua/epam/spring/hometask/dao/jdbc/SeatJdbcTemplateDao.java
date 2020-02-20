package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.SeatDao;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.mappers.SeatMapper;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

@Repository
public class SeatJdbcTemplateDao implements SeatDao {

    private static final String SELECT_FROM_SEATS_WHERE_ID = "select * from Seats where id = ?";
    private static final String SELECT_FROM_SEATS_WHERE_AUDITORIUM_ID = "select * from Seats where auditorium_id = ?";
    private static final String SELECT_FROM_SEATS = "select * from Seats";
    private static final String INSERT_USER_INTO_SEATS = "insert into Seats (number, type, auditorium_id) values (?,?,?)";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Seat save(@Nonnull Seat object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_INTO_SEATS, new String[]{"ID"});
            statement.setLong(1, object.getNumber());
            statement.setString(2, object.getSeatType().name());
            statement.setInt(3, object.getAuditoriumId());
            return statement;
        }, keyHolder);

        return getById(String.valueOf(keyHolder.getKey()));
    }

    @Override
    public void remove(@Nonnull Seat object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Seat getById(@Nonnull String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_SEATS_WHERE_ID, new Object[]{id}, new SeatMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Seat not found by id: " + id);
        }
    }

    @Nonnull
    @Override
    public Collection<Seat> getAll() {
        return jdbcTemplate.query(SELECT_FROM_SEATS, new SeatMapper());
    }


    @Override
    public Collection<Seat> getByAuditoriumId(@Nonnull String auditoriumId) {
        try {
            return jdbcTemplate.query(SELECT_FROM_SEATS_WHERE_AUDITORIUM_ID, new Object[]{auditoriumId}, new SeatMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Seat not found by auditoriumId" + auditoriumId);
        }
    }
}
