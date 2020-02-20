package ua.epam.spring.hometask.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.storage.Store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BookingServiceImplTest extends BaseTest {

    private static BookingService bookingService;
    private static EventService eventService;
    private static AuditoriumService auditoriumService;
    private static Store store;

    private static Event event1, event2;
    private static LocalDateTime airDateTime1, airDateTime2;
    private static User user1;
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

        store = (Store) context.getBean("store");

        user1 = new User();
        user1.setFirstName("Daniel");
        user1.setLastName("Klein");
        user1.setEmail("d.klein@gmail.com");
        user1.setBirthDate(LocalDate.of(1980, Month.DECEMBER, 17));

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

        event1 = new Event();
        event1.setName("Knives Out");
        event1.setBasePrice(100);
        event1.setRating(EventRating.MID);
        event1.setAirDates(set);
        event1.setAuditoriums(dateAndEvent1);

        event2 = new Event();
        event2.setName("Shindler's list");
        event2.setBasePrice(80);
        event2.setRating(EventRating.MID);
        event2.setAirDates(set);
        event2.setAuditoriums(dateAndEvent2);

        seat1 = new Seat();
        seat1.setNumber(1L);

        seat2 = new Seat();
        seat2.setNumber(2L);

        ticket1 = new Ticket(user1, event1, airDateTime1, seat1);
        ticket2 = new Ticket(user1, event1, airDateTime1, seat2);
        ticket3 = new Ticket(user1, event2, airDateTime2, seat1);
        ticket4 = new Ticket(user1, event2, airDateTime2, seat2);

    }

    @After
    public void cleanUp() {
        store.getEventMap().clear();
        store.getEventCounterMap().clear();
    }

    @Before
    public void saveEvent() {
        eventService.save(event1);
    }

    @Test
    public void shouldReturnTicketPriceWithDiscountIfUserHasBirthdayInSameAirDate() {
        assertEquals(BigDecimal.valueOf(190.00).setScale(2, BigDecimal.ROUND_CEILING),
                bookingService.getTicketsPrice(event1, airDateTime1, user1, seats));
    }

    @Test
    public void shouldBookTickets() {
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket1, ticket2)));
        assertEquals(2, store.getTicketMap().values().size());

    }

    @Test
    public void shouldReturnPurchasedTicketsForEvent() {
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket1, ticket2)));
        assertEquals(2, bookingService.getPurchasedTicketsForEvent(event1, airDateTime1).size());
    }

    @Test
    public void shouldReturnHowManyTimesTicketsForEventBooked() {
        eventService.save(event2);
        bookingService.bookTickets(new HashSet<>(Arrays.asList(
                ticket1,
                ticket2,
                ticket3)));

        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket4)));

        assertEquals(1, store.getEventCounterMap().get(ticket1.getEventId().getName()).getEventTicketsBookedCount());
        assertEquals(2, store.getEventCounterMap().get(ticket3.getEventId().getName()).getEventTicketsBookedCount());
    }

    @Test
    public void shouldReturnHowManyTimesEventPricesWereQueried() {
        bookingService.getTicketsPrice(event1, airDateTime1, user1, seats);
        bookingService.getTicketsPrice(event1, airDateTime1, user1, seats);

        eventService.save(event2);

        bookingService.getTicketsPrice(event2, airDateTime2, user1, seats);
        bookingService.getTicketsPrice(event2, airDateTime2, user1, seats);
        bookingService.getTicketsPrice(event2, airDateTime2, user1, seats);

        assertEquals(2, store.getEventCounterMap().get(event1.getName()).getEventPriceCalledCount());
        assertEquals(3, store.getEventCounterMap().get(event2.getName()).getEventPriceCalledCount());
    }
}