package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class Ticket extends DomainObject implements Comparable<Ticket> {

    private String userId;

    private String eventId;

    private LocalDateTime airDateTime;

    private String seatId;

    private boolean booked;

    public Ticket() {
    }

    public Ticket(User user, Event event, LocalDateTime airDateTime, Seat seat, boolean booked) {
        this.userId = user.getId();
        this.eventId = event.getId();
        this.airDateTime = airDateTime;
        this.seatId = String.valueOf(seat.getNumber());
        this.booked = booked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getAirDateTime() {
        return airDateTime;
    }

    public void setAirDateTime(LocalDateTime airDateTime) {
        this.airDateTime = airDateTime;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }


    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }
        int result = airDateTime.compareTo(other.getAirDateTime());

        if (result == 0) {
            result = eventId.compareTo(other.getEventId());
        }
        if (result == 0) {
            result = seatId.compareTo(other.getSeatId());
        }

        if (result == 0) {
            result = userId.compareTo(other.getUserId());
        }
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "user=" + userId +
                ", event=" + eventId +
                ", dateTime=" + airDateTime +
                ", seat=" + seatId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return booked == ticket.booked &&
                userId.equals(ticket.userId) &&
                eventId.equals(ticket.eventId) &&
                airDateTime.equals(ticket.airDateTime) &&
                seatId.equals(ticket.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId, airDateTime, seatId, booked);
    }
}
