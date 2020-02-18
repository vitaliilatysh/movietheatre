package ua.epam.spring.hometask.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.service.EventService;

import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class EventServiceImplTest extends BaseTest {

    public static final String DELETE_FROM_EVENTS = "DELETE FROM Events";
    private static EventService eventService;
    private static Event event1, event2, event3;
    private static JdbcTemplate jdbcTemplate;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        eventService = context.getBean(EventService.class);
        jdbcTemplate = context.getBean(JdbcTemplate.class);

        event2 = new Event();
        event2.setName("Shindler's list");
        event2.setBasePrice(80);
        event2.setRating(EventRating.MID);
        event2.setAirDates(new TreeSet<>());
        event2.setAuditoriums(new TreeMap<>());

        event3 = new Event();
        event3.setName("Vulkan");
        event3.setBasePrice(60);
        event3.setRating(EventRating.HIGH);
        event3.setAirDates(new TreeSet<>());
        event3.setAuditoriums(new TreeMap<>());

    }

    @After
    public void cleanUp() {
        jdbcTemplate.update(DELETE_FROM_EVENTS);
    }

    @Before
    public void saveEvent() {
        event1 = new Event();
        event1.setName("Knives Out");
        event1.setBasePrice(100);
        event1.setRating(EventRating.MID);
        event1.setAirDates(new TreeSet<>());
        event1.setAuditoriums(new TreeMap<>());

        event1 = eventService.save(event1);
    }

    @Test
    public void shouldSaveNewEvent() {
        assertEquals(event2, eventService.save(event2));
    }


    @Test
    public void shouldReturnByName() {
        assertEquals(event1, eventService.getByName(event1.getName()));
    }

    @Test
    public void shouldThrowExceptionIfEventNotFoundBySuchName() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("Event not found by name " + event3.getName());

        eventService.getByName(event3.getName());
    }

    @Test
    public void shouldThrowExceptionEventWithSuchNameAlreadyRegistered() {
        expectedEx.expect(ItemAlreadyExistException.class);
        expectedEx.expectMessage("Event already registered");

        eventService.save(event1);
    }

    @Test
    public void shouldRemoveRegisteredEvent() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("Event not found by id: " + event1.getId());

        eventService.remove(event1);
        eventService.getById(event1.getId());
    }

    @Test
    public void shouldThrowExceptionIfRegisteredEventNotFound() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("Event not found by id: " + event2.getId());

        eventService.remove(event2);
    }

    @Test
    public void shouldReturnEventById() {
        Event foundEvent = eventService.getById(event1.getId());
        assertEquals(foundEvent, event1);
    }

    @Test
    public void shouldReturnAllRegisteredEvent() {
        eventService.save(event2);
        assertEquals(2, eventService.getAll().size());
    }

//    @Test
//    public void shouldReturnHowManyTimesEventCallingByName() {
//        eventService.save(event3);
//
//        eventService.getByName(event1.getName());
//        eventService.getByName(event1.getName());
//
//        eventService.getByName(event3.getName());
//        eventService.getByName(event3.getName());
//        eventService.getByName(event3.getName());
//
//
//        assertEquals(2, store.getEventCounterMap().get(event1.getName()).getEventCalledByNameCount());
//        assertEquals(3, store.getEventCounterMap().get(event3.getName()).getEventCalledByNameCount());
//    }
}