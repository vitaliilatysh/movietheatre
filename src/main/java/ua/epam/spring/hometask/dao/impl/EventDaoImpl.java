package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class EventDaoImpl implements EventDao {

    private Store store;

    public EventDaoImpl(Store store) {
        this.store = store;
    }

    @Override
    public Event getByName(@Nonnull String eventName) {
        Optional<Event> foundEvent = store.getEventMap().values().stream()
                .filter(event -> event.getName().equalsIgnoreCase(eventName))
                .findAny();
        return foundEvent.orElseThrow(() -> new ItemNotFoundException("Event not found by email" + eventName));
    }

    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public Collection<Event> getNextEvents(LocalDateTime to) {
        return null;
    }

    @Override
    public Event save(@Nonnull Event object) {
        long uniqueID = Long.valueOf(UUID.randomUUID().toString());
        store.getEventMap().put(uniqueID, object);
        return store.getEventMap().get(uniqueID);
    }

    @Override
    public void remove(@Nonnull Event object) {
        Optional.ofNullable(store.getEventMap().remove(object.getId()))
                .orElseThrow(() -> new ItemNotFoundException("Event not found: " + object));
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return Optional.of(store.getEventMap().get(id))
                .orElseThrow(() -> new ItemNotFoundException("Event not found by id: " + id));
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return store.getEventMap().values();
    }
}
