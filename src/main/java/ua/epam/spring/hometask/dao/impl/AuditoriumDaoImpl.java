package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.domain.SeatType;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.util.*;

public class AuditoriumDaoImpl implements AuditoriumDao {

    private Store store;
    private List<String> names;
    private List<Long> seats;
    private List<Long> vipSeats;

    public AuditoriumDaoImpl(Store store, List<String> names, List<Long> seats, List<Long> vipSeats) {
        this.store = store;
        this.names = names;
        this.seats = seats;
        this.vipSeats = vipSeats;
    }

    public void init() {
        for (int i = 0; i < names.size(); i++) {
            Set<Seat> regulars = new TreeSet<>();
            Set<Seat> vips = new TreeSet<>();

            String uniqueID = UUID.randomUUID().toString();
            Auditorium auditorium = new Auditorium();
            auditorium.setId(uniqueID);
            auditorium.setName(names.get(i));

            for (Long seatNumber = 1L; seatNumber <= seats.get(i); seatNumber++) {
                Seat seat = new Seat();
                seat.setNumber(seatNumber);
                seat.setSeatType(SeatType.REGULAR);
                regulars.add(seat);
            }
            auditorium.setRegularSeats(regulars);

            for (Long vipSeatNumber = seats.get(i) + 1; vipSeatNumber <= seats.get(i) + vipSeats.get(i); vipSeatNumber++) {
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
