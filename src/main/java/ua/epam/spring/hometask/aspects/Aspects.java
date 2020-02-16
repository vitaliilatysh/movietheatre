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
        Object[] args = joinPoint.getArgs();
        String eventName = ((Event) args[0]).getName();
        System.out.println("getTicketsPrice() called");
    }

    @After("getEventByName() && args(eventName,..)")
    public void countEventCallingByName(String eventName) {
        Map<String, Counter> eventNameAndCallingAmount = store.getCountEventCallByName();

        if (eventNameAndCallingAmount.containsKey(eventName)) {
            Counter existedCounter = eventNameAndCallingAmount.get(eventName);
            int newCount = existedCounter.getEventCalledByNameCount() + 1;
            existedCounter.setEventCalledByNameCount(newCount);
            eventNameAndCallingAmount.put(eventName, existedCounter);
            return;
        }
        Counter counter = new Counter();
        counter.setEventCalledByNameCount(1);
        eventNameAndCallingAmount.put(eventName, counter);

    }
}
