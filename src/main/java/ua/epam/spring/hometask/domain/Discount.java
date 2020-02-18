package ua.epam.spring.hometask.domain;

public class Discount extends DomainObject {
    private String userid;
    private StrategyType typeDiscount;

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userId) {
        this.userid = userId;
    }

    public StrategyType getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(StrategyType typeDiscount) {
        this.typeDiscount = typeDiscount;
    }
}
