package ua.epam.spring.hometask.storage;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private static Store instance;
    private Map<String, User> userMap;
    private Map<String, Ticket> ticketMap;
    private Map<String, Event> eventMap;
    private Map<String, Auditorium> auditoriumMap;

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

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, Ticket> getTicketMap() {
        return ticketMap;
    }

    public Map<String, Auditorium> getAuditoriumMap() {
        return auditoriumMap;
    }

    public Map<String, Event> getEventMap() {
        return eventMap;
    }
}
