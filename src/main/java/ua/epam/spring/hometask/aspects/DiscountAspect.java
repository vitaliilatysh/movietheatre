package ua.epam.spring.hometask.aspects;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.StrategyType;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.strategy.StrategyParams;

import java.math.BigDecimal;

@Aspect
@Component
public class DiscountAspect {

    @Autowired
    private DiscountService discountService;

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
        discount.setUser(params.getUser());
        discount.setTypeDiscount(StrategyType.BIRTHDAY);

        discountService.save(discount);
    }

    @AfterReturning(pointcut = "getNticketDiscount(params)", returning = "result")
    public void storeNticketDiscount(StrategyParams params, BigDecimal result) {

        if (result.equals(BigDecimal.ZERO)) return;

        Discount discount = new Discount();
        discount.setUser(params.getUser());
        discount.setTypeDiscount(StrategyType.N_TICKET);

        discountService.save(discount);
    }

}
