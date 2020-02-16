package ua.epam.spring.hometask.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.storage.Store;

import java.util.Map;

@Aspect
@Component
public class Aspects {

    @Autowired
    private Store store;

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.getTicketsPrice(..))")
    public void getTicketPrice() {

    }

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.EventServiceImpl.getByName(..))")
    public void getEventByName() {
    }

    @After("getTicketPrice()")
    public void counterTicketPriceCalling() {
        System.out.println("getTicketsPrice() called");
    }

    @After("getEventByName() && args(eventName,..)")
    public void countEventCallingByName(JoinPoint joinPoint, String eventName) {
        Map<String, Integer> eventNameAndCallingAmount = store.getCountEventCallByName();

        if (eventNameAndCallingAmount.containsKey(eventName)) {
            eventNameAndCallingAmount.put(eventName, eventNameAndCallingAmount.get(eventName) + 1);
            return;
        }
        eventNameAndCallingAmount.put(eventName, 1);

    }
}
