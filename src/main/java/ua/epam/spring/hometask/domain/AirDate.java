package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class AirDate extends DomainObject implements Comparable<AirDate> {

    private LocalDateTime airDate;
    private Event event;

    public AirDate() {
    }

    public LocalDateTime getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDateTime airDate) {
        this.airDate = airDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirDate airDate1 = (AirDate) o;
        return airDate.equals(airDate1.airDate) &&
                event.equals(airDate1.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airDate, event);
    }

    @Override
    public int compareTo(AirDate other) {
        if (other == null) {
            return 1;
        }
        int result = airDate.compareTo(other.airDate);

        if (result != 0) {
            return 1;
        }

        return result;


    }
}
