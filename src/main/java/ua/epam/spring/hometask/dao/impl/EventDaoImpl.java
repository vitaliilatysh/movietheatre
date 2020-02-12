package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EventDaoImpl implements EventDao {

    @Autowired
    private Store store;

    public EventDaoImpl() {
    }

    @Override
    public Event getByName(@Nonnull String eventName) {
        Optional<Event> foundEvent = store.getEventMap().values().stream()
                .filter(event -> event.getName().equalsIgnoreCase(eventName))
                .findAny();
        return foundEvent.orElseThrow(() -> new ItemNotFoundException("Event not found by name" + eventName));
    }

    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Event> getNextEvents(LocalDateTime to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Event save(@Nonnull Event object) {
        Optional<Event> event = store.getEventMap().values().stream()
                .filter(user -> user.getName().equalsIgnoreCase(object.getName()))
                .findAny();
        if (event.isPresent()) {
            throw new ItemAlreadyExistException("Event already registered with such name " + object.getName());
        }

        String uniqueID = UUID.randomUUID().toString();
        object.setId(uniqueID);

        store.getEventMap().put(uniqueID, object);
        return store.getEventMap().get(uniqueID);
    }

    @Override
    public void remove(@Nonnull Event object) {
        String eventId = object.getId();
        Event foundEvent = store.getEventMap().get(eventId);
        if (foundEvent == null) {
            throw new ItemNotFoundException("Event not found by id: " + eventId);
        }
        store.getEventMap().remove(eventId);
    }

    @Override
    public Event getById(@Nonnull String id) {
        Event foundEvent = store.getEventMap().get(id);
        if (foundEvent == null) {
            throw new ItemNotFoundException("Event not found by id: " + id);
        }
        return foundEvent;
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return store.getEventMap().values();
    }
}
