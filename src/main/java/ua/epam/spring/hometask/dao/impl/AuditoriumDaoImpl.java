package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.domain.SeatType;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AuditoriumDaoImpl implements AuditoriumDao {

    private Store store;
    private List<String> names;
    private int seats;
    private int vipSeats;

    public AuditoriumDaoImpl(Store store, List<String> names, int seats, int vipSeats) {
        this.store = store;
        this.names = names;
        this.seats = seats;
        this.vipSeats = vipSeats;
    }

    public void init() {
        Set<Seat> regulars = new TreeSet<>();
        Set<Seat> vips = new TreeSet<>();

        for (int seatNumber = 1; seatNumber <= seats; seatNumber++) {
            Seat seat = new Seat();
            seat.setNumber(seatNumber);
            seat.setSeatType(SeatType.REGULAR);
            regulars.add(seat);
        }

        for (int seatNumber = seats + 1; seatNumber < (seats + vipSeats); seatNumber++) {
            Seat seat = new Seat();
            seat.setNumber(seatNumber);
            seat.setSeatType(SeatType.VIP);
        }

        names.forEach(name -> {
            String uniqueID = UUID.randomUUID().toString();
            Auditorium auditorium = new Auditorium();
            auditorium.setId(uniqueID);
            auditorium.setName(name);
            auditorium.setRegularSeats(regulars);
            auditorium.setVipSeats(vips);

            store.getAuditoriumMap().put(uniqueID, auditorium);
        });
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
