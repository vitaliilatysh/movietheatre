package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

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
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {

        if (user.getBirthDate().equals(airDateTime.toLocalDate())) {

        }
        return 0;
    }

    public List<DiscountStrategy> getDiscountStrategy() {
        return discountStrategy;
    }

    public void setDiscountStrategy(List<DiscountStrategy> discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
}
