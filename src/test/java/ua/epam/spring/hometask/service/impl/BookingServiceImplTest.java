package ua.epam.spring.hometask.service.impl;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BookingServiceImplTest extends BaseTest {

    private static BookingService bookingService;
    private static EventService eventService;
    private static UserService userService;
    private static AuditoriumService auditoriumService;
    private static CounterService counterService;
    private static JdbcTemplate jdbcTemplate;

    private static Event event1, event2;
    private static LocalDateTime airDateTime1, airDateTime2;
    private static User user1, user2;
    private static Set<Long> seats;
    private static Seat seat1, seat2;
    private static Ticket ticket1, ticket2, ticket3, ticket4;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        eventService = context.getBean(EventService.class);
        auditoriumService = context.getBean(AuditoriumService.class);
        bookingService = context.getBean(BookingService.class);
        userService = context.getBean(UserService.class);
        jdbcTemplate = context.getBean(JdbcTemplate.class);
        counterService = context.getBean(CounterService.class);

        airDateTime1 = LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0);
        airDateTime2 = LocalDateTime.of(2020, Month.DECEMBER, 18, 11, 30);

        NavigableSet<LocalDateTime> set = new TreeSet<>();
        set.add(airDateTime1);
        set.add(airDateTime2);

        seats = new HashSet<>(Arrays.asList(1L, 2L));

        NavigableMap<LocalDateTime, Auditorium> dateAndEvent1 = new TreeMap<>();
        dateAndEvent1.put(airDateTime1, auditoriumService.getByName("Kyiv"));

        NavigableMap<LocalDateTime, Auditorium> dateAndEvent2 = new TreeMap<>();
        dateAndEvent2.put(airDateTime2, auditoriumService.getByName("Panorama"));

        user1 = new User();
        user1.setFirstName("Daniel");
        user1.setLastName("Klein");
        user1.setEmail("d.klein@gmail.com");
        user1.setBirthDate(LocalDate.of(1980, Month.DECEMBER, 17));

        user2 = new User();
        user2.setFirstName("Fill");
        user2.setLastName("Krock");
        user2.setEmail("f.krock@gmail.com");
        user2.setBirthDate(LocalDate.of(1985, Month.JANUARY, 5));

        event1 = new Event();
        event1.setName("Knives Out");
        event1.setBasePrice(100);
        event1.setRating(EventRating.MID);

        event2 = new Event();
        event2.setName("Shindler's list");
        event2.setBasePrice(80);
        event2.setRating(EventRating.MID);


        user1 = userService.save(user1);
        user2 = userService.save(user2);

        event1 = eventService.save(event1);
        event1.setAirDates(set);
        event1.setAuditoriums(dateAndEvent1);

        event2 = eventService.save(event2);
        event2.setAirDates(set);
        event2.setAuditoriums(dateAndEvent2);

        seat1 = new Seat();
        seat1.setNumber(1L);

        seat2 = new Seat();
        seat2.setNumber(2L);

        ticket1 = new Ticket(user1, event1, airDateTime1, seat1, false);
        ticket2 = new Ticket(user1, event1, airDateTime1, seat2, false);
        ticket3 = new Ticket(user2, event2, airDateTime2, seat1, false);
        ticket4 = new Ticket(user2, event2, airDateTime2, seat2, false);
    }

    @After
    public void cleanUp() {
        jdbcTemplate.update(DELETE_FROM_TICKETS);
        jdbcTemplate.update(DELETE_FROM_COUNTERS);
    }

    @Test
    public void shouldReturnTicketPriceWithDiscountIfUserHasBirthdayInSameAirDate() {
        assertEquals(BigDecimal.valueOf(190.00).setScale(2, BigDecimal.ROUND_CEILING),
                bookingService.getTicketsPrice(event1, airDateTime1, user1, seats));
    }

    @Test
    public void shouldBookTickets() {
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket3, ticket4)));
        assertEquals(2, bookingService.getPurchasedTicketsForUser(user2).size());

    }

    @Test
    public void shouldReturnPurchasedTicketsForEvent() {
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket3, ticket4)));
        assertEquals(2, bookingService.getPurchasedTicketsForEvent(event2, airDateTime2).size());
    }

    @Test
    public void shouldReturnHowManyTimesTicketsForEventBooked() {
        bookingService.bookTickets(new HashSet<>(Arrays.asList(
                ticket1,
                ticket2,
                ticket3)));

        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket4)));

        assertEquals(1, counterService.getCountByTicketBooked(event1));
        assertEquals(2, counterService.getCountByTicketBooked(event2));
    }

    @Test
    public void shouldReturnHowManyTimesEventPricesWereQueried() {
        bookingService.getTicketsPrice(event1, airDateTime1, user1, seats);
        bookingService.getTicketsPrice(event1, airDateTime1, user1, seats);

        bookingService.getTicketsPrice(event2, airDateTime2, user1, seats);
        bookingService.getTicketsPrice(event2, airDateTime2, user1, seats);
        bookingService.getTicketsPrice(event2, airDateTime2, user1, seats);

        assertEquals(2, counterService.getCountByPrice(event1));
        assertEquals(3, counterService.getCountByPrice(event2));
    }
}