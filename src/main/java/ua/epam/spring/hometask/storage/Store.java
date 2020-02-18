package ua.epam.spring.hometask.storage;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class Store {
    private List<Discount> discountList = new LinkedList<>();
    private Map<String, Counter> eventCounter = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();
    private Map<String, Ticket> ticketMap = new HashMap<>();
    private Map<String, Event> eventMap = new HashMap<>();
    private Map<String, Auditorium> auditoriumMap = new HashMap<>();

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

    public Map<String, Counter> getEventCounterMap() {
        return eventCounter;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }
}
