package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.mappers.TicketMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

@Repository
public class TicketJdbcTemplateDao implements TicketDao {

    private static final String SELECT_FROM_TICKETS = "select * from Tickets";
    private static final String SELECT_FROM_TICKETS_WHERE_ID = "select * from Tickets where id = ?";
    private static final String INSERT_TICKET_INTO_TICKETS = "insert into Tickets (airDate, user_id, event_id, seat_id) values (?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private AuditoriumDao auditoriumDao;


    @Override
    public void remove(@Nonnull Ticket object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ticket save(@Nonnull Ticket object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        check(object);

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_TICKET_INTO_TICKETS, new String[]{"ID"});
            statement.setDate(1, Date.valueOf(object.getAirDateTime().toLocalDate()));
            statement.setString(2, object.getUserId());
            statement.setString(2, object.getEventId());
            statement.setString(2, object.getSeatId());
            return statement;
        }, keyHolder);

        return getById(String.valueOf(keyHolder.getKey()));
    }

    @Override
    public Ticket getById(@Nonnull String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_TICKETS_WHERE_ID, new Object[]{id}, new TicketMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Discount not found by id: " + id);
        }
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return jdbcTemplate.query(SELECT_FROM_TICKETS, new TicketMapper());
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        return 0;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {

    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return null;
    }
}
