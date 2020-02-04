package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;

@Repository
public class AuditoriumDaoImpl implements AuditoriumDao {

    @Autowired
    private Store store;

    @PostConstruct
    private void init() {
        //read properties file and save it to store
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
