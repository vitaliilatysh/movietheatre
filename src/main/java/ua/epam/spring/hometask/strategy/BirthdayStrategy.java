package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class BirthdayStrategy implements DiscountStrategy {

    @Override
    public byte count(@Nullable User user,
                      @Nonnull Event event,
                      @Nonnull LocalDateTime airDateTime,
                      long numberOfTickets) {
        return (byte) 5;
    }
}
