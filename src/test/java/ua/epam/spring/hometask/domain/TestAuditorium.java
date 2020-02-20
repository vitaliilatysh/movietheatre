package ua.epam.spring.hometask.domain;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Yuriy_Tkach
 */
@RunWith(JUnit4.class)
public class TestAuditorium {

    private Seat seat1, seat2, seat3;

    @Before
    public void init() {
        seat1 = new Seat();
        seat1.setNumber(1L);
        seat1.setSeatType(SeatType.REGULAR);
        seat2 = new Seat();
        seat2.setNumber(2L);
        seat2.setSeatType(SeatType.VIP);
        seat3 = new Seat();
        seat3.setNumber(3L);
        seat3.setSeatType(SeatType.REGULAR);
    }

    @Test
    @Ignore
    public void shouldReturnVipSeats() {
        Auditorium a = new Auditorium();
//        a.setVipSeats(Stream.of(seat1, seat2, seat3).collect(Collectors.toSet()));
//        assertEquals(1, a.countVipSeats(Arrays.asList(seat1, seat2, seat3)));
    }

    @Test
    @Ignore
    public void shouldReturnAllSeats() {
        Auditorium a = new Auditorium();
//        a.setRegularSeats(Stream.of(seat1, seat2).collect(Collectors.toSet()));
//        assertEquals(2, a.getAllSeats().size());
    }

}
