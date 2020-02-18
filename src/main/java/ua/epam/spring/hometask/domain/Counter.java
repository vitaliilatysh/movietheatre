package ua.epam.spring.hometask.domain;

public class Counter {

    private int eventCalledByNameCount;
    private int eventPriceCalledCount;
    private int eventTicketsBookedCount;

    public int getEventCalledByNameCount() {
        return eventCalledByNameCount;
    }

    public void setEventCalledByNameCount(int eventCalledByNameCount) {
        this.eventCalledByNameCount = eventCalledByNameCount;
    }

    public int getEventPriceCalledCount() {
        return eventPriceCalledCount;
    }

    public void setEventPriceCalledCount(int eventPriceCalledCount) {
        this.eventPriceCalledCount = eventPriceCalledCount;
    }

    public int getEventTicketsBookedCount() {
        return eventTicketsBookedCount;
    }

    public void setEventTicketsBookedCount(int eventTicketsBookedCount) {
        this.eventTicketsBookedCount = eventTicketsBookedCount;
    }
}
