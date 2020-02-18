package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.SeatType;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.mappers.AuditoriumMapper;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 18.02.2020
 */
@DependsOn("initSchemaBean")
@Repository
public class AuditoriumJdbcTemplateDao implements AuditoriumDao {

    private static final String INSERT_INTO_AUDITORIUMS = "insert into Auditoriums (name) values (?)";
    private static final String INSERT_INTO_SEATS = "insert into Seats (number, type, booked, auditorium_id) values (?,?,?,?)";
    private static final String SELECT_FROM_AUDITORIUMS = "select * from Auditoriums";
    private static final String SELECT_FROM_AUDITORIUMS_WHERE_NAME = "select * from Auditoriums where name = ?";

    @Value("#{'${auditorium.name}'.split(',')}")
    private String[] names;

    @Value("#{'${auditorium.seats}'.split(',')}")
    private String[] seats;

    @Value("#{'${auditorium.seats.vip}'.split(',')}")
    private String[] vipSeats;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        int index = 0;
        for (String auditoriumName : names) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(INSERT_INTO_AUDITORIUMS, new String[]{"ID"});
                statement.setString(1, auditoriumName);
                return statement;
            }, keyHolder);

            Number auditoriumId = keyHolder.getKey();

            for (int seatNumber = 1; seatNumber <= Integer.valueOf(seats[index]); seatNumber++) {
                jdbcTemplate.update(INSERT_INTO_SEATS, seatNumber, SeatType.REGULAR.name(), false, auditoriumId);
            }

            for (int vipSeatNumber = Integer.valueOf(seats[index]) + 1; vipSeatNumber <= Integer.valueOf(seats[index]) + Integer.valueOf(vipSeats[index]); vipSeatNumber++) {
                jdbcTemplate.update(INSERT_INTO_SEATS, vipSeatNumber, SeatType.VIP.name(), false, auditoriumId);
            }

            index++;
        }
    }

    @Override
    public Auditorium getByName(@Nonnull String auditoriumName) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_AUDITORIUMS_WHERE_NAME, new Object[]{auditoriumName}, new AuditoriumMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Auditorium not found by name " + auditoriumName);
        }
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(@Nonnull Auditorium object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Auditorium getById(@Nonnull String id) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return jdbcTemplate.query(SELECT_FROM_AUDITORIUMS, new AuditoriumMapper());
    }
}
