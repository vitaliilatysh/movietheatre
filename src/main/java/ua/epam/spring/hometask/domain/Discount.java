package ua.epam.spring.hometask.domain;

public class Discount extends DomainObject {
    private String userId;
    private StrategyType typeDiscount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public StrategyType getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(StrategyType typeDiscount) {
        this.typeDiscount = typeDiscount;
    }
}
