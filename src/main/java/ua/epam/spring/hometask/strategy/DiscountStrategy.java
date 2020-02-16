package ua.epam.spring.hometask.strategy;

import java.math.BigDecimal;

public interface DiscountStrategy {

    /**
     * @param params strategy params
     * @return the discount
     */
    BigDecimal count(StrategyParams params);
}
