package ua.epam.spring.hometask.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.storage.Counter;
import ua.epam.spring.hometask.storage.Store;

import java.util.Map;

@Aspect
@Component
public class Aspects {

    @Autowired
    private Store store;

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.getTicketsPrice(..))")
    public void getTicketsPrice() {

    }

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.EventServiceImpl.getByName(..))")
    public void getEventByName() {
    }

    @After("getTicketsPrice()")
    public void counterTicketPriceCalling(JoinPoint joinPoint) {
        Map<String, Counter> eventCounterMap = store.getEventCounterMap();
        Object[] args = joinPoint.getArgs();
        String eventName = ((Event) args[0]).getName();

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
