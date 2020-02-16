package ua.epam.spring.hometask.storage;

import ua.epam.spring.hometask.domain.User;

public class Discount {
    private User user;
    private Class typeDiscount;

    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public Class getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(Class typeDiscount) {
        this.typeDiscount = typeDiscount;
    }
}
