package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class EveryNTicketStrategy implements DiscountStrategy {

    private static final byte DISCOUNT = 50;
    private static final byte N_TICKET_DISCOUNT = 10;

    @Override
    public byte count(@Nullable User user,
                      @Nonnull Event event,
                      @Nonnull LocalDateTime airDateTime,
                      long numberOfTickets) {
        byte winTicketsNumber = (byte) (numberOfTickets / N_TICKET_DISCOUNT);
        byte totalDiscount = 0;

        if (numberOfTickets >= 1) {
            totalDiscount = (byte) Math.round((double) DISCOUNT * winTicketsNumber / numberOfTickets);
        }

        return totalDiscount;
    }
}
