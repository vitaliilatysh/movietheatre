package ua.epam.spring.hometask.domain;

public class Discount extends DomainObject {
    private User user;
    private StrategyType typeDiscount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StrategyType getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(StrategyType typeDiscount) {
        this.typeDiscount = typeDiscount;
    }
}
