package ua.epam.spring.hometask.aspects;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.storage.Store;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class CounterAspect {

    @Autowired
    private Store store;

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.getTicketsPrice(..)) && args(event,..)")
    public void getTicketsPrice(Event event) {
    }

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.bookTickets(..)) && args(tickets,..)")
    public void bookTickets(Set<Ticket> tickets) {
    }

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.EventServiceImpl.getByName(..))")
    public void getEventByName() {
    }

    @After("bookTickets(tickets)")
    public void counterTicketsBookingForEvent(Set<Ticket> tickets) {
        Map<String, Counter> eventCounterMap = store.getEventCounterMap();

        List<String> events = tickets.stream().map(ticket -> ticket.getEvent().getName()).distinct()
                .collect(Collectors.toList());

        events.forEach(eventName -> {
            if (eventCounterMap.containsKey(eventName)) {
                Counter existedCounter = eventCounterMap.get(eventName);
                int newCount = existedCounter.getEventTicketsBookedCount() + 1;
                existedCounter.setEventTicketsBookedCount(newCount);
                eventCounterMap.put(eventName, existedCounter);
                return;
            }
            Counter counter = new Counter();
            counter.setEventTicketsBookedCount(1);
            eventCounterMap.put(eventName, counter);
        });
    }

    @After("getTicketsPrice(event)")
    public void counterTicketPriceCalling(Event event) {
        Map<String, Counter> eventCounterMap = store.getEventCounterMap();
        String eventName = event.getName();

        if (eventCounterMap.containsKey(eventName)) {
            Counter existedCounter = eventCounterMap.get(eventName);
            int newCount = existedCounter.getEventPriceCalledCount() + 1;
            existedCounter.setEventPriceCalledCount(newCount);
            eventCounterMap.put(eventName, existedCounter);
            return;
        }
        Counter counter = new Counter();
        counter.setEventPriceCalledCount(1);
        eventCounterMap.put(eventName, counter);
    }

    @After("getEventByName() && args(eventName,..)")
    public void countEventCallingByName(String eventName) {
        Map<String, Counter> eventCounterMap = store.getEventCounterMap();

        if (eventCounterMap.containsKey(eventName)) {
            Counter existedCounter = eventCounterMap.get(eventName);
            int newCount = existedCounter.getEventCalledByNameCount() + 1;
            existedCounter.setEventCalledByNameCount(newCount);
            eventCounterMap.put(eventName, existedCounter);
            return;
        }
        Counter counter = new Counter();
        counter.setEventCalledByNameCount(1);
        eventCounterMap.put(eventName, counter);

    }
}
