package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.domain.SeatType;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class AuditoriumDaoImpl implements AuditoriumDao {

    @Autowired
    private Store store;

    @Value("#{'${auditorium.name}'.split(',')}")
    private String[] names;

    @Value("#{'${auditorium.seats}'.split(',')}")
    private String[] seats;

    @Value("#{'${auditorium.seats.vip}'.split(',')}")
    private String[] vipSeats;

    public AuditoriumDaoImpl() {
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < names.length; i++) {
            Set<Seat> regulars = new TreeSet<>();
            Set<Seat> vips = new TreeSet<>();

            String uniqueID = UUID.randomUUID().toString();
            Auditorium auditorium = new Auditorium();
            auditorium.setId(uniqueID);
            auditorium.setName(names[i]);

            for (Long seatNumber = 1L; seatNumber <= Long.valueOf(seats[i]); seatNumber++) {
                Seat seat = new Seat();
                seat.setNumber(seatNumber);
                seat.setSeatType(SeatType.REGULAR);
                regulars.add(seat);
            }
            auditorium.setRegularSeats(regulars);

            for (Long vipSeatNumber = Long.valueOf(seats[i]) + 1; vipSeatNumber <= Long.valueOf(seats[i]) + Long.valueOf(vipSeats[i]); vipSeatNumber++) {
                Seat seat = new Seat();
                seat.setNumber(vipSeatNumber);
                seat.setSeatType(SeatType.VIP);
                vips.add(seat);
            }
            auditorium.setVipSeats(vips);

            store.getAuditoriumMap().put(uniqueID, auditorium);
        }
    }

    @Override
    public Auditorium getByName(@Nonnull String auditoriumName) {
        Optional<Auditorium> foundAuditorium = store.getAuditoriumMap().values().stream()
                .filter(event -> event.getName().equalsIgnoreCase(auditoriumName))
                .findAny();
        return foundAuditorium.orElseThrow(() -> new ItemNotFoundException("Auditorium not found by name " + auditoriumName));
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(@Nonnull Auditorium object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Auditorium getById(@Nonnull String id) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return store.getAuditoriumMap().values();
    }

}
