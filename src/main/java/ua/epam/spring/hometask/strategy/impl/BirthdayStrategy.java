package ua.epam.spring.hometask.strategy.impl;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.strategy.DiscountStrategy;
import ua.epam.spring.hometask.strategy.StrategyParams;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class BirthdayStrategy implements DiscountStrategy {

    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.05);

    public BigDecimal count(StrategyParams params) {
        LocalDateTime airDateTime = params.getAirDateTime();
        BigDecimal totalSum = params.getTotalSum();

        LocalDate userBirthDate = params.getUser().getBirthDate();

        if (userBirthDate == null) {
            return BigDecimal.ZERO;
        }
        if (diffInDays(userBirthDate, airDateTime) >= 0 && diffInDays(userBirthDate, airDateTime) <= 5) {
            return totalSum.multiply(DISCOUNT);
        }
        return BigDecimal.ZERO;
    }

    private int diffInDays(LocalDate userBirthDate, @Nonnull LocalDateTime airDateTime) {
        return airDateTime.toLocalDate().getDayOfYear() - userBirthDate.getDayOfYear();
    }
}
