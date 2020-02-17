package ua.epam.spring.hometask.storage;

import ua.epam.spring.hometask.domain.StrategyType;
import ua.epam.spring.hometask.domain.User;

public class Discount {
    private User user;
    private StrategyType typeDiscount;

    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public StrategyType getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(StrategyType typeDiscount) {
        this.typeDiscount = typeDiscount;
    }
}
