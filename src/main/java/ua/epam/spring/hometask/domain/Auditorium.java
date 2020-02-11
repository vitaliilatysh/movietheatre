package ua.epam.spring.hometask.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public class Auditorium extends DomainObject {

    private String name;
    private Set<Seat> regularSeats = Collections.emptySet();
    private Set<Seat> vipSeats = Collections.emptySet();

    public Auditorium() {
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     *
     * @param seats Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return vipSeats.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRegularSeats() {
        return regularSeats.size();
    }

    public void setRegularSeats(Set<Seat> regularSeats) {
        this.regularSeats = regularSeats;
    }

    public Set<Seat> getAllSeats() {
        regularSeats.addAll(vipSeats);
        return regularSeats;
    }

    public Set<Seat> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Set<Seat> vipSeats) {
        this.vipSeats = vipSeats;
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
