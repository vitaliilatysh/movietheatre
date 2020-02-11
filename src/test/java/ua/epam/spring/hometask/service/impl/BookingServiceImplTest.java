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
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.storage.Store;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

@RunWith(JUnit4.class)
public class BookingServiceImplTest extends BaseTest {

    private static EventService eventService;
    private static AuditoriumService auditoriumService;
    private static Event event1;
    private static Event event2;
    private static Store store;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        eventService = (EventService) context.getBean("eventService");
        auditoriumService = (AuditoriumService) context.getBean("auditoriumService");

        store = (Store) context.getBean("store");

        LocalDateTime airDateTime1 = LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0);
        LocalDateTime airDateTime2 = LocalDateTime.of(2020, Month.DECEMBER, 18, 11, 30);

        NavigableSet<LocalDateTime> set = new TreeSet<>();
        set.add(airDateTime1);
        set.add(airDateTime2);


        NavigableMap<LocalDateTime, Auditorium> dateAndEvent = new TreeMap<>();
        dateAndEvent.put(airDateTime1, auditoriumService.getByName("Kyiv"));
        dateAndEvent.put(airDateTime1, auditoriumService.getByName("Panorama"));
        dateAndEvent.put(airDateTime2, auditoriumService.getByName("Planeta Kino"));

        event1 = new Event();
        event1.setName("Knives Out");
        event1.setBasePrice(100);
        event1.setRating(EventRating.MID);
        event1.setAirDates(set);
        event1.setAuditoriums(dateAndEvent);

        event2 = new Event();
        event2.setName("Shindler's list");
        event2.setBasePrice(80);
        event2.setRating(EventRating.MID);
        event2.setAirDates(new TreeSet<>());
        event2.setAuditoriums(new TreeMap<>());

    }

    @After
    public void cleanUp() {
        store.getEventMap().clear();
    }

    @Before
    public void saveEvent() {
        eventService.save(event1);
    }

    @Test
    public void getTicketsPrice() {
    }

    @Test
    public void bookTickets() {
    }

    @Test
    public void getPurchasedTicketsForEvent() {
    }
}