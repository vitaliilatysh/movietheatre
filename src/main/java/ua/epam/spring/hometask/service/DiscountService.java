package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yuriy_Tkach
 */
public interface DiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     *
     * @param user        User that buys tickets. Can be <code>null</code>
     * @param event       Event that tickets are bought for
     * @param airDateTime The date and time event will be aired
     * @param seatsAmount Number of seats that user buys
     * @param totalPrice  total buying tickets price
     * @return how much points from total price will be dropped
     */
    BigDecimal getDiscount(@Nullable User user,
                           @Nonnull Event event,
                           @Nonnull LocalDateTime airDateTime,
                           int seatsAmount,
                           BigDecimal totalPrice);

}
