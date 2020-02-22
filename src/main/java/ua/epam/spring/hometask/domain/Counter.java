package ua.epam.spring.hometask.domain;

public class Counter extends DomainObject {

    private int eventCalledByNameCount;
    private int eventPriceCalledCount;
    private int eventTicketsBookedCount;
    private Event event;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
