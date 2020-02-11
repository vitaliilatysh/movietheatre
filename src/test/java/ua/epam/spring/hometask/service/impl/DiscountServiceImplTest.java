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

import java.math.BigDecimal;
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
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(45, discount.intValue());
    }

    @Test
    public void shouldReturnBirthdayDiscountIfUserBirthdayWithinNDayOfEventAirDate() {
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 19, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(45, discount.intValue());
    }

    @Test
    public void shouldNotReturnDiscountIfUserBirthdayBeforeTheEventAirDate() {
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 16, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(0, discount.intValue());
    }

    @Test
    public void shouldNotReturnDiscountIfUserBirthdayAfterNDayTheEventAirDate() {
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(0, discount.intValue());
    }


    @Test
    public void shouldNotReturnDiscountIfUserByLessThenNTickets() {
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(0, discount.intValue());
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets() {
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                10, BigDecimal.valueOf(1000));

        assertEquals(50, discount.intValue());
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets1Time() {
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                19, BigDecimal.valueOf(1900));

        assertEquals(50, discount.intValue());
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets2Times() {
        BigDecimal discount = discountService.getDiscount(
                user,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                20, BigDecimal.valueOf(2000));

        assertEquals(100, discount.intValue());
    }
}