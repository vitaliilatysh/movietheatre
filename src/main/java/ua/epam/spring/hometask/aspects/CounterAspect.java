package ua.epam.spring.hometask.aspects;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class CounterAspect {

    @Autowired
    private CounterDao counterDao;

    @Autowired
    private EventDao eventDao;

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
        List<String> eventIds = tickets.stream().map(Ticket::getEventId).distinct()
                .collect(Collectors.toList());

        for (String eventId : eventIds) {
            Event event = eventDao.getById(eventId);
            Counter foundCounter = counterDao.getByEvent(event);
            if (foundCounter == null) {
                Counter counter = new Counter();
                counter.setEventTicketsBookedCount(1);
                counter.setEvent(event);
                counterDao.save(counter);
                continue;
            }
            foundCounter.setEventTicketsBookedCount(foundCounter.getEventTicketsBookedCount() + 1);
            foundCounter.setEvent(event);
            counterDao.update(foundCounter, foundCounter.getEventTicketsBookedCount(), "tickets_booked");
        }
    }

    @After("getTicketsPrice(event)")
    public void counterTicketPriceCalling(Event event) {
        Counter foundCounter = counterDao.getByEvent(event);
        if (foundCounter == null) {
            Counter counter = new Counter();
            counter.setEventPriceCalledCount(1);
            counter.setEvent(event);
            counterDao.save(counter);
            return;
        }
        foundCounter.setEventPriceCalledCount(foundCounter.getEventPriceCalledCount() + 1);
        foundCounter.setEvent(event);
        counterDao.update(foundCounter, foundCounter.getEventPriceCalledCount(), "price_called");
    }

    @After("getEventByName() && args(eventName,..)")
    public void countEventCallingByName(String eventName) {
        Event foundEvent = eventDao.getByName(eventName);
        Counter foundCounter = counterDao.getByEvent(foundEvent);
        if (foundCounter == null) {
            Counter counter = new Counter();
            counter.setEventCalledByNameCount(1);
            counter.setEvent(foundEvent);
            counterDao.save(counter);
            return;
        }
        foundCounter.setEventCalledByNameCount(foundCounter.getEventCalledByNameCount() + 1);
        foundCounter.setEvent(foundEvent);
        counterDao.update(foundCounter, foundCounter.getEventCalledByNameCount(), "name_called");
    }
}
