package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.strategy.DiscountStrategy;
import ua.epam.spring.hometask.strategy.impl.BirthdayStrategy;
import ua.epam.spring.hometask.strategy.impl.EveryNTicketStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private List<DiscountStrategy> discountStrategy;

    public DiscountServiceImpl() {
    }

    @Override
    public BigDecimal getDiscount(@Nullable User user,
                                  @Nonnull Event event,
                                  @Nonnull LocalDateTime airDateTime,
                                  int orderedSeats,
                                  BigDecimal totalSum) {
        BigDecimal everyNTicketDiscount;
        BigDecimal birthDateDiscount;

        if (user == null) {
            return checkEveryNTicketDiscount(event, orderedSeats, totalSum);
        }

        everyNTicketDiscount = checkEveryNTicketDiscount(event, orderedSeats, totalSum);
        birthDateDiscount = checkBirthdayDiscount(user, event, airDateTime, totalSum);

        if (birthDateDiscount.compareTo(everyNTicketDiscount) > 0) {
            return birthDateDiscount;
        }
        return everyNTicketDiscount;
    }

    private BigDecimal checkEveryNTicketDiscount(Event event, int seatsAmount, BigDecimal totalSum) {
        BigDecimal result = BigDecimal.ZERO;
        if (seatsAmount >= 10) {
            Optional<DiscountStrategy> first = discountStrategy.stream()
                    .filter(strategy -> strategy instanceof EveryNTicketStrategy).findFirst();

            if (first.isPresent()) {
                result = first.get().count(event, seatsAmount, totalSum);
            }
        }
        return result;
    }

    private BigDecimal checkBirthdayDiscount(User user, Event event, @Nonnull LocalDateTime airDateTime, BigDecimal totalSum) {
        LocalDate userBirthDate = user.getBirthDate();
        BigDecimal result = BigDecimal.ZERO;

        if (diffInDays(userBirthDate, airDateTime) >= 0 && diffInDays(userBirthDate, airDateTime) <= 5) {
            Optional<DiscountStrategy> first = discountStrategy.stream()
                    .filter(strategy -> strategy instanceof BirthdayStrategy).findFirst();
            if (first.isPresent()) {
                result = first.get().count(event, 0, totalSum);
            }
        }
        return result;
    }

    private int diffInDays(LocalDate userBirthDate, @Nonnull LocalDateTime airDateTime) {
        return airDateTime.toLocalDate().getDayOfYear() - userBirthDate.getDayOfYear();
    }

    public List<DiscountStrategy> getDiscountStrategy() {
        return discountStrategy;
    }

    public void setDiscountStrategy(List<DiscountStrategy> discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
}
