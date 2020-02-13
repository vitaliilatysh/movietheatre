package ua.epam.spring.hometask.aspects;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspects {

    @Pointcut("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.getTicketsPrice(..))")
    public void getTicketPrice() {

    }

    @After("getTicketPrice()")
    public void counterTicketPriceCalling() {
        System.out.println("getTicketsPrice() called");
    }
}
