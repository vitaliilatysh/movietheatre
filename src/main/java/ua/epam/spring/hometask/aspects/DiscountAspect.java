package ua.epam.spring.hometask.aspects;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.storage.Discount;
import ua.epam.spring.hometask.storage.Store;
import ua.epam.spring.hometask.strategy.StrategyParams;
import ua.epam.spring.hometask.strategy.impl.BirthdayStrategy;
import ua.epam.spring.hometask.strategy.impl.EveryNTicketStrategy;

import java.math.BigDecimal;

@Aspect
@Component
public class DiscountAspect {

    @Autowired
    private Store store;

    @Pointcut("execution(* ua.epam.spring.hometask.strategy.impl.EveryNTicketStrategy.count(..)) && args(params,..)")
    public void getNticketDiscount(StrategyParams params) {
    }

    @Pointcut("execution(* ua.epam.spring.hometask.strategy.impl.BirthdayStrategy.count(..)) && args(params,..)")
    public void getBirthdayDiscount(StrategyParams params) {
    }

    @AfterReturning(pointcut = "getBirthdayDiscount(params)", returning = "result")
    public void storeBirthdayDiscount(StrategyParams params, BigDecimal result) {

        if (result.equals(BigDecimal.ZERO)) return;

        Discount discount = new Discount();
        discount.setUserId(params.getUser());
        discount.setTypeDiscount(BirthdayStrategy.class);

        store.getDiscountList().add(discount);

    }

    @AfterReturning(pointcut = "getNticketDiscount(params)", returning = "result")
    public void storeNticketDiscount(StrategyParams params, BigDecimal result) {

        if (result.equals(BigDecimal.ZERO)) return;

        Discount discount = new Discount();
        discount.setUserId(params.getUser());
        discount.setTypeDiscount(EveryNTicketStrategy.class);

        store.getDiscountList().add(discount);
    }

}
