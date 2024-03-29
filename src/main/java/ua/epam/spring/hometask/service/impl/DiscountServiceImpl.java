package ua.epam.spring.hometask.service.impl;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.DiscountDao;
import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.strategy.DiscountStrategy;
import ua.epam.spring.hometask.strategy.StrategyParams;
import ua.epam.spring.hometask.strategy.impl.BirthdayStrategy;
import ua.epam.spring.hometask.strategy.impl.EveryNTicketStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscountServiceImpl implements DiscountService {

    private Map<Class<?>, DiscountStrategy> strategyMap = new HashMap<>();

    @Autowired
    private DiscountDao discountDao;

    @Autowired
    public void setImageLoaders(List<DiscountStrategy> discountStrategies) {
        discountStrategies.forEach(strategy -> {
            Class<?> strategyClass = ((Advised) strategy).getTargetSource().getTargetClass();
            strategyMap.put(strategyClass, strategy);
        });
    }

    @Override
    public BigDecimal getDiscount(@Nullable User user,
                                  @Nonnull Event event,
                                  @Nonnull LocalDateTime airDateTime,
                                  int orderedSeats,
                                  BigDecimal totalSum) {
        StrategyParams strategyParams = new StrategyParams();
        strategyParams.setUser(user);
        strategyParams.setEvent(event);
        strategyParams.setAirDateTime(airDateTime);
        strategyParams.setOrderedSeats(orderedSeats);
        strategyParams.setTotalSum(totalSum);

        BigDecimal everyNTicketDiscount = strategyMap.get(EveryNTicketStrategy.class).count(strategyParams);
        BigDecimal birthDateDiscount = strategyMap.get(BirthdayStrategy.class).count(strategyParams);

        if (birthDateDiscount.compareTo(everyNTicketDiscount) > 0) {
            return birthDateDiscount;
        }
        return everyNTicketDiscount;
    }

    @Nullable
    @Override
    public Collection<Discount> getByType(@Nonnull String type) {
        return discountDao.getByType(type);
    }

    @Override
    public Discount save(@Nonnull Discount object) {
        return discountDao.save(object);
    }

    @Override
    public void remove(@Nonnull Discount object) {
        discountDao.remove(object);
    }

    @Override
    public Discount getById(@Nonnull String id) {
        return discountDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Discount> getAll() {
        return discountDao.getAll();
    }
}
