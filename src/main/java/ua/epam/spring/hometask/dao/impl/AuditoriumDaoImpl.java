package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class AuditoriumDaoImpl implements AuditoriumDao {

    private Store store;
    private Auditorium auditorium;

    public AuditoriumDaoImpl(Store store, Auditorium auditorium) {
        this.auditorium = auditorium;
        this.store = store;
    }

    public void init() {
        store.getAuditoriumMap().put(1L, auditorium);
    }

    @Override
    public Auditorium getByName(@Nonnull String auditoriumName) {
        Optional<Auditorium> foundAuditorium = store.getAuditoriumMap().values().stream()
                .filter(event -> event.getName().equalsIgnoreCase(auditoriumName))
                .findAny();
        return foundAuditorium.orElseThrow(() -> new ItemNotFoundException("Auditorium not found by email" + auditoriumName));
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        //not supported
        return null;
    }

    @Override
    public void remove(@Nonnull Auditorium object) {
        //not supported
    }

    @Override
    public Auditorium getById(@Nonnull Long id) {
        //not supported
        return null;
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return store.getAuditoriumMap().values();
    }
}
