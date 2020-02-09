package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class BirthdayStrategy implements DiscountStrategy {

    private static final byte BIRTHDAY_DISCOUNT = 5;

    @Override
    public byte count(@Nullable User user,
                      @Nonnull Event event,
                      @Nonnull LocalDateTime airDateTime,
                      long numberOfTickets) {

        return BIRTHDAY_DISCOUNT;
    }
}
