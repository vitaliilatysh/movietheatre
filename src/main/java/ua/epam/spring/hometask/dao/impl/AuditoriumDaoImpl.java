package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.util.*;

public class AuditoriumDaoImpl implements AuditoriumDao {

    private Store store;
    private List<String> names;
    private List<Long> seats;
    private List<Long> vipSeats;

    public AuditoriumDaoImpl(Store store) {
        this.store = store;
    }

    public void init() {
        for (int i = 0; i < names.size(); i++) {
            String uniqueID = UUID.randomUUID().toString();
            Auditorium auditorium = new Auditorium();
            auditorium.setId(uniqueID);
            auditorium.setName(names.get(i));
            auditorium.setNumberOfSeats(seats.get(i));
            auditorium.setVipSeats(new HashSet<>(Collections.singletonList(vipSeats.get(i))));

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

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<Long> getSeats() {
        return seats;
    }

    public void setSeats(List<Long> seats) {
        this.seats = seats;
    }

    public List<Long> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(List<Long> vipSeats) {
        this.vipSeats = vipSeats;
    }
}
