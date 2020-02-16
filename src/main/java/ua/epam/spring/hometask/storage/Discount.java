package ua.epam.spring.hometask.storage;

public class Discount {
    private String userId;
    private Class typeDiscount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Class getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(Class typeDiscount) {
        this.typeDiscount = typeDiscount;
    }
}
