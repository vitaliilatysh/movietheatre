package ua.epam.spring.hometask.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StrategyParams {

    private BigDecimal totalSum;
    private int orderedSeats;
    private LocalDateTime airDateTime;
    private User user;
    private Event event;

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public int getOrderedSeats() {
        return orderedSeats;
    }

    public void setOrderedSeats(int orderedSeats) {
        this.orderedSeats = orderedSeats;
    }

    public LocalDateTime getAirDateTime() {
        return airDateTime;
    }

    public void setAirDateTime(LocalDateTime airDateTime) {
        this.airDateTime = airDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
