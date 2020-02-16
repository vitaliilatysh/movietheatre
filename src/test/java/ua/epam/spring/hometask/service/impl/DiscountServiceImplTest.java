package ua.epam.spring.hometask.service.impl;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.storage.Store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DiscountServiceImplTest extends BaseTest {

    private static DiscountService discountService;
    private static Store store;

    private static User user1, user2, user3;
    private static Event event;
    private static LocalDateTime airDateTime;

    @BeforeClass
    public static void setUp() {
        discountService = context.getBean(DiscountService.class);
        store = context.getBean(Store.class);

        airDateTime = LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0);

        NavigableSet<LocalDateTime> set = new TreeSet<>();
        set.add(airDateTime);

        event = new Event();
        event.setName("Knives Out");
        event.setBasePrice(100);
        event.setRating(EventRating.MID);
        event.setAirDates(set);
        event.setAuditoriums(new TreeMap<>());

        user1 = new User();
        user1.setId("1");
        user1.setFirstName("Daniel");
        user1.setLastName("Klein");
        user1.setEmail("d.klein@gmail.com");
        user1.setBirthDate(LocalDate.of(1980, Month.DECEMBER, 17));

        user2 = new User();
        user2.setId("2");
        user2.setFirstName("Mark");
        user2.setLastName("Reitar");
        user2.setEmail("m.reitar@gmail.com");
        user2.setBirthDate(LocalDate.of(1988, Month.OCTOBER, 17));

        user3 = new User();
        user3.setFirstName("Marko");
        user3.setLastName("Galanevych");
        user3.setEmail("m.galanevych@gmail.com");
        user3.setBirthDate(LocalDate.of(1984, Month.JULY, 7));
    }

    @After
    public void clean() {
        store.getDiscountList().clear();
    }

    @Test
    public void shouldReturnBirthdayDiscountIfEventAirDateEqualsUserBirthday() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(45, discount.intValue());
    }

    @Test
    public void shouldReturnBirthdayDiscountIfUserBirthdayWithinNDayOfEventAirDate() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 19, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(45, discount.intValue());
    }

    @Test
    public void shouldNotReturnDiscountIfUserBirthdayBeforeTheEventAirDate() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 16, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(0, discount.intValue());
    }

    @Test
    public void shouldNotReturnDiscountIfUserBirthdayAfterNDayTheEventAirDate() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(0, discount.intValue());
    }


    @Test
    public void shouldNotReturnDiscountIfUserByLessThenNTickets() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(0, discount.intValue());
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                10, BigDecimal.valueOf(1000));

        assertEquals(50, discount.intValue());
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets1Time() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                19, BigDecimal.valueOf(1900));

        assertEquals(50, discount.intValue());
    }

    @Test
    public void shouldReturnDiscountIfUserByNTickets2Times() {
        BigDecimal discount = discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                20, BigDecimal.valueOf(2000));

        assertEquals(100, discount.intValue());
    }

    @Test
    public void shouldReturnHowManyTimesUserReceivedTheDiscount() {
        //Discount for N ticket for user1
        discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                20, BigDecimal.valueOf(2000));

        //Birthday discount for user1
        discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0),
                9, BigDecimal.valueOf(900));

        assertEquals(2, store.getDiscountList().size());

    }

    @Test
    public void shouldReturnHowManyTimesEachUserReceivedTheDiscount() {
        //Discount for N ticket for user1
        discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                20, BigDecimal.valueOf(2000));

        //Birthday discount for user1
        discountService.getDiscount(
                user1,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 17, 12, 0),
                9, BigDecimal.valueOf(900));

        //Discount for N ticket for user2
        discountService.getDiscount(
                user2,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                20, BigDecimal.valueOf(2000));

        //Discount for N ticket for not registered user
        discountService.getDiscount(
                user3,
                event,
                LocalDateTime.of(2020, Month.DECEMBER, 23, 12, 0),
                20, BigDecimal.valueOf(2000));

        assertEquals(2, store.getDiscountList().stream()
                .filter(discount -> discount.getUser().equals(user1))
                .count());
        assertEquals(1, store.getDiscountList().stream()
                .filter(discount -> discount.getUser().equals(user2))
                .count());

        assertEquals(4, store.getDiscountList().size());

    }
}