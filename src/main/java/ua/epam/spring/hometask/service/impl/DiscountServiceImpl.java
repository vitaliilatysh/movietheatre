package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.strategy.BirthdayStrategy;
import ua.epam.spring.hometask.strategy.DiscountStrategy;
import ua.epam.spring.hometask.strategy.EveryNTicketStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DiscountServiceImpl implements DiscountService {

    private UserService userService;
    private EventService eventService;
    private List<DiscountStrategy> discountStrategy;

    public DiscountServiceImpl(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    public DiscountServiceImpl() {
    }

    @Override
    public BigDecimal getDiscount(@Nullable User user,
                                  @Nonnull Event event,
                                  @Nonnull LocalDateTime airDateTime,
                                  long seats, long vipSeats) {
        BigDecimal totalSum = BigDecimal.ZERO;
        BigDecimal everyNTicketDiscount;
        BigDecimal birthDateDiscount;

        totalSum = getPrimarySum(event, seats, vipSeats, totalSum);

        if (user == null) {
            return checkEveryNTicketDiscount(event, seats, vipSeats, totalSum);
        }

        everyNTicketDiscount = checkEveryNTicketDiscount(event, seats, vipSeats, totalSum);
        birthDateDiscount = checkBirthdayDiscount(user, event, airDateTime, seats, vipSeats, totalSum);

        if (birthDateDiscount.compareTo(everyNTicketDiscount) > 0) {
            return birthDateDiscount;
        }
        return everyNTicketDiscount;
    }

    private BigDecimal getPrimarySum(@Nonnull Event event, long seats, long vipSeats, BigDecimal totalSum) {
        BigDecimal basePrice = BigDecimal.valueOf(event.getBasePrice());
        BigDecimal ticketsNumber = BigDecimal.valueOf(seats);
        BigDecimal vipTicketsNumber = BigDecimal.valueOf(vipSeats);


        if (vipTicketsNumber.intValue() != 0 && ticketsNumber.intValue() != 0) {
            totalSum = basePrice.multiply(ticketsNumber)
                    .add(vipTicketsNumber).multiply(BigDecimal.valueOf(2));
        }
        if (vipTicketsNumber.intValue() == 0 && ticketsNumber.intValue() != 0) {
            totalSum = basePrice.multiply(ticketsNumber);
        }

        if (vipTicketsNumber.intValue() != 0 && ticketsNumber.intValue() == 0) {
            totalSum = basePrice.multiply(vipTicketsNumber).multiply(BigDecimal.valueOf(2));
        }

        if (EventRating.HIGH.equals(event.getRating())) {
            totalSum = totalSum.multiply(BigDecimal.valueOf(1.2));
        }
        return totalSum;
    }

    private BigDecimal checkEveryNTicketDiscount(Event event, long seats, long vipSeats, BigDecimal totalSum) {
        BigDecimal result = BigDecimal.ZERO;
        if (seats + vipSeats >= 10) {
            Optional<DiscountStrategy> first = discountStrategy.stream()
                    .filter(strategy -> strategy instanceof EveryNTicketStrategy).findFirst();

            if (first.isPresent()) {
                result = first.get().count(event, seats, vipSeats, totalSum);
            }
        }
        return result;
    }

    private BigDecimal checkBirthdayDiscount(User user, Event event, @Nonnull LocalDateTime airDateTime, long seats, long vipSeats, BigDecimal totalSum) {
        LocalDate userBirthDate = user.getBirthDate();
        BigDecimal result = BigDecimal.ZERO;

        if (diffInDays(userBirthDate, airDateTime) >= 0 && diffInDays(userBirthDate, airDateTime) <= 5) {
            Optional<DiscountStrategy> first = discountStrategy.stream()
                    .filter(strategy -> strategy instanceof BirthdayStrategy).findFirst();
            if (first.isPresent()) {
                result = first.get().count(event, seats, vipSeats, totalSum);
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
