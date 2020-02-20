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

    public Ticket() {
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

    @Override
    public int hashCode() {
        return Objects.hash(airDateTime, eventId, seatId);
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
        Ticket other = (Ticket) obj;
        if (airDateTime == null) {
            if (other.airDateTime != null) {
                return false;
            }
        } else if (!airDateTime.equals(other.airDateTime)) {
            return false;
        }
        if (eventId == null) {
            if (other.eventId != null) {
                return false;
            }
        } else if (!eventId.equals(other.eventId)) {
            return false;
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return seatId.equals(other.seatId);
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
}
