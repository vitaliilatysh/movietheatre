package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.*;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.exceptions.TicketAlreadyBookedException;
import ua.epam.spring.hometask.mappers.TicketMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

@Repository
public class TicketJdbcTemplateDao implements TicketDao {

    private static final int MULTIPLE_PRICE_FOR_VIP_SEATS_IN_TIMES = 2;
    private static final double MULTIPLE_PRICE_FOR_RATED_MOVIES_IN_TIMES = 1.2d;

    private static final String SELECT_BOOKED_TICKETS = "select * from Tickets where event_id = ? and booked = ? and airDate = ?";
    private static final String SELECT_BOOKED_TICKETS_FOR_USER = "select * from Tickets where user_id = ? and booked = ?";
    private static final String SELECT_BOOKED_TICKETS_BY_USER_ID = "select * from Tickets where user_id = ?";
    private static final String SELECT_FROM_TICKETS_WHERE_ID = "select * from Tickets where id = ?";
    private static final String INSERT_TICKET_INTO_TICKETS = "insert into Tickets (airDate, user_id, event_id, seat_id, booked) values (?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private AuditoriumDao auditoriumDao;

    @Autowired
    private SeatDao seatDao;


    @Override
    public void remove(@Nonnull Ticket object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ticket save(@Nonnull Ticket object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_TICKET_INTO_TICKETS, new String[]{"ID"});
            statement.setTimestamp(1, Timestamp.valueOf(object.getAirDateTime()));
            statement.setString(2, object.getUserId());
            statement.setString(3, object.getEventId());
            statement.setString(4, object.getSeatId());
            statement.setBoolean(5, true);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        if (auditorium == null) {
            throw new ItemNotFoundException("Auditorium not found by air date time " + dateTime.toString());
        }

        Set<Seat> foundSeats = seatDao.getAll().stream()
                .filter(seat -> seats.contains(seat.getNumber()))
                .collect(Collectors.toSet());

        double result = 0d;
        double basePrice = event.getBasePrice();
        long regularTicketsNumber = auditorium.countRegularSeats(foundSeats);
        long vipTicketsNumber = auditorium.countVipSeats(foundSeats);

        if (vipTicketsNumber != 0 && regularTicketsNumber != 0) {
            result = (basePrice * regularTicketsNumber) + vipTicketsNumber * MULTIPLE_PRICE_FOR_VIP_SEATS_IN_TIMES;
        }
        if (vipTicketsNumber == 0 && regularTicketsNumber != 0) {
            result = basePrice * regularTicketsNumber;
        }

        if (vipTicketsNumber != 0 && regularTicketsNumber == 0) {
            result = basePrice * vipTicketsNumber * MULTIPLE_PRICE_FOR_VIP_SEATS_IN_TIMES;
        }

        if (EventRating.HIGH.equals(event.getRating())) {
            result = result * MULTIPLE_PRICE_FOR_RATED_MOVIES_IN_TIMES;
        }
        return result;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        Optional<Ticket> isAnyTicketBooked = tickets.stream().filter(Ticket::isBooked).findAny();

        if (isAnyTicketBooked.isPresent()) {
            throw new TicketAlreadyBookedException("Already booked " + isAnyTicketBooked.get().toString());
        }

        tickets.forEach(this::save);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return new HashSet<>(jdbcTemplate.query(SELECT_BOOKED_TICKETS, new Object[]{event.getId(), true, dateTime}, new TicketMapper()));
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForUser(@Nonnull User user) {
        return new HashSet<>(jdbcTemplate.query(SELECT_BOOKED_TICKETS_FOR_USER, new Object[]{user.getId(), true}, new TicketMapper()));

    }

    @Override
    public Set<Ticket> getTicketsByUser(@Nonnull User user) {
        return new HashSet<>(jdbcTemplate.query(SELECT_BOOKED_TICKETS_BY_USER_ID, new Object[]{user.getId()}, new TicketMapper()));
    }
}
