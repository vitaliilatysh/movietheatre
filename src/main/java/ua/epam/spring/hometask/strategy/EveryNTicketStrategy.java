package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class EveryNTicketStrategy implements DiscountStrategy {

    @Override
    public byte count(@Nullable User user,
                      @Nonnull Event event,
                      @Nonnull LocalDateTime airDateTime,
                      long numberOfTickets) {
        long numberTicketWin = numberOfTickets / 10;

        return 0;
    }
}
