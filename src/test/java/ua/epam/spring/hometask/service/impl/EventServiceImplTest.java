package ua.epam.spring.hometask.service.impl;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.storage.Store;

import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class EventServiceImplTest extends BaseTest {

    private static EventService eventService;
    private static Event event1;
    private static Event event2;
    private static Event event3;
    private static Store store;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        eventService = context.getBean(EventService.class);
        store = context.getBean(Store.class);

        event1 = new Event();
        event1.setName("Knives Out");
        event1.setBasePrice(100);
        event1.setRating(EventRating.MID);
        event1.setAirDates(new TreeSet<>());
        event1.setAuditoriums(new TreeMap<>());

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
        store.getEventMap().clear();
        store.getEventCounterMap().clear();
    }

    @Before
    public void saveEvent() {
        eventService.save(event1);
    }

    @Test
    public void shouldSaveNewUser() {
        assertEquals(event2, eventService.save(event2));
    }


    @Test
    public void shouldReturnByName() {
        assertEquals(event1, eventService.getByName(event1.getName()));
    }

    @Test
    public void shouldThrowExceptionIfEventNotFoundBySuchName() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("Event not found by name" + event2.getName());

        eventService.getByName(event2.getName());
    }

    @Test
    public void shouldThrowExceptionEventWithSuchNameAlreadyRegistered() {
        expectedEx.expect(ItemAlreadyExistException.class);
        expectedEx.expectMessage("Event already registered with such name " + event1.getName());

        eventService.save(event1);
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

    @Test
    public void shouldReturnHowManyTimesEventCallingByName() {
        eventService.save(event3);

        eventService.getByName(event1.getName());
        eventService.getByName(event1.getName());

        eventService.getByName(event3.getName());
        eventService.getByName(event3.getName());
        eventService.getByName(event3.getName());


        assertEquals(2, store.getEventCounterMap().get(event1.getName()).getEventCalledByNameCount());
        assertEquals(3, store.getEventCounterMap().get(event3.getName()).getEventCalledByNameCount());
    }
}