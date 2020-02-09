package ua.epam.spring.hometask.service.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DiscountServiceImplTest extends BaseTest {

    private static DiscountService discountService;
    private static User user;
    private static Event event;

    @BeforeClass
    public static void setUp() {
        discountService = (DiscountService) context.getBean("discountService");

        event = new Event();
        event.setName("Knives Out");
        event.setBasePrice(100);
        event.setRating(EventRating.MID);
        event.setAirDates(new TreeSet<>());
        event.setAuditoriums(new TreeMap<>());

        user = new User();
        user.setFirstName("Daniel");
        user.setLastName("Klein");
        user.setEmail("d.klein@gmail.com");
        user.setBirthDate(LocalDate.of(1980, Month.DECEMBER, 17));
    }

    @Test
    public void shouldReturnBirthdayDiscountIfEventAirDateEqualsUserBirthday() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0),
                9);

        assertEquals(5, discount);
    }

    @Test
    public void shouldReturnBirthdayDiscountIfUserBirthdayWithinNDayOfEventAirDate() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 19, 12, 0),
                9);

        assertEquals(5, discount);
    }

    @Test
    public void shouldReturn0DiscountIfUserBirthdayBeforeTheEventAirDate() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 16, 12, 0),
                9);

        assertEquals(0, discount);
    }

    @Test
    public void shouldReturn0DiscountIfUserBirthdayAfterNDayTheEventAirDate() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                9);

        assertEquals(0, discount);
    }


    @Test
    public void shouldNotReturnDiscountIfUserByLessThenNTickets() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                9);

        assertEquals(0, discount);
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                10);

        assertEquals(5, discount);
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets1Time() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                19);

        assertEquals(3, discount);
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets2Times() {
        byte discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                20);

        assertEquals(5, discount);
    }
}