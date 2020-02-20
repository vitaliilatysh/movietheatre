package ua.epam.spring.hometask.domain;

import java.util.Objects;

/**
 * @author Vitalii Latysh
 * Created: 11.02.2020
 */
public class Seat extends DomainObject implements Comparable<Seat> {

    private Long number;
    private SeatType seatType;
    private int auditoriumId;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return number.equals(seat.number)
                && seatType == seat.seatType
                && auditoriumId == (seat.auditoriumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, seatType, auditoriumId);
    }

    @Override
    public int compareTo(Seat another) {
        if (another == null) {
            return 0;
        }
        int result = this.getSeatType().compareTo(another.getSeatType());

        if (result == 0) {
            if (!this.getNumber().equals(another.getNumber())) {
                result = 1;
            }
        }
        return result;
    }

    public int getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(int auditoriumId) {
        this.auditoriumId = auditoriumId;
    }
}
