package ua.epam.spring.hometask.domain;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class Auditorium extends DomainObject {

    private String name;

    public Auditorium() {
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     *
     * @param seats Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Seat> seats) {
        return seats.stream().filter(seat -> seat.getSeatType().equals(SeatType.VIP)).count();
    }

    public long countRegularSeats(Collection<Seat> seats) {
        return seats.stream().filter(seat -> seat.getSeatType().equals(SeatType.REGULAR)).count();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Auditorium other = (Auditorium) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

}
