package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Seat;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;
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
import java.util.Set;

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
                                  @Nonnull Set<Long> seats,
                                  BigDecimal totalSum) {
        BigDecimal everyNTicketDiscount;
        BigDecimal birthDateDiscount;

        Auditorium auditorium = event.getAuditoriums()
        if (user == null) {
            return checkEveryNTicketDiscount(event, seats, totalSum);
        }

        everyNTicketDiscount = checkEveryNTicketDiscount(event, seats, totalSum);
        birthDateDiscount = checkBirthdayDiscount(user, event, airDateTime, seats, totalSum);

        if (birthDateDiscount.compareTo(everyNTicketDiscount) > 0) {
            return birthDateDiscount;
        }
        return everyNTicketDiscount;
    }

    private BigDecimal checkEveryNTicketDiscount(Event event, Set<Seat> seats, BigDecimal totalSum) {
        BigDecimal result = BigDecimal.ZERO;
        if (seats.size() >= 10) {
            Optional<DiscountStrategy> first = discountStrategy.stream()
                    .filter(strategy -> strategy instanceof EveryNTicketStrategy).findFirst();

            if (first.isPresent()) {
                result = first.get().count(event, seats, totalSum);
            }
        }
        return result;
    }

    private BigDecimal checkBirthdayDiscount(User user, Event event, @Nonnull LocalDateTime airDateTime, Set<Seat> seats, BigDecimal totalSum) {
        LocalDate userBirthDate = user.getBirthDate();
        BigDecimal result = BigDecimal.ZERO;

        if (diffInDays(userBirthDate, airDateTime) >= 0 && diffInDays(userBirthDate, airDateTime) <= 5) {
            Optional<DiscountStrategy> first = discountStrategy.stream()
                    .filter(strategy -> strategy instanceof BirthdayStrategy).findFirst();
            if (first.isPresent()) {
                result = first.get().count(event, seats, totalSum);
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
