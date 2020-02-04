package ua.epam.spring.hometask.storage;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private static Store instance;
    private Map<Long, User> userMap;
    private Map<Long, Ticket> ticketMap;
    private Map<Long, Event> eventMap;
    private Map<Long, Auditorium> auditoriumMap;

    private Store() {
        this.userMap = new HashMap<>();
        this.ticketMap = new HashMap<>();
        this.eventMap = new HashMap<>();
        this.auditoriumMap = new HashMap<>();
    }

    public static Store getStoreInstance() {
        if (instance == null) {
            synchronized (Store.class) {
                if (instance == null) {
                    instance = new Store();
                }
            }
        }
        return instance;
    }

    public Map<Long, User> getUserMap() {
        return userMap;
    }

    public Map<Long, Ticket> getTicketMap() {
        return ticketMap;
    }

    public Map<Long, Auditorium> getAuditoriumMap() {
        return auditoriumMap;
    }

    public Map<Long, Event> getEventMap() {
        return eventMap;
    }
}
