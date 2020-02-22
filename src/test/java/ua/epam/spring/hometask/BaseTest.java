package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.AppConfig;

public abstract class BaseTest {

    protected static final String DELETE_FROM_EVENTS = "DELETE FROM Events;COMMIT";
    protected static final String DELETE_FROM_USERS = "DELETE FROM Users;COMMIT";
    protected static final String DELETE_FROM_DISCOUNTS = "DELETE FROM Discounts;COMMIT";
    protected static final String DELETE_FROM_TICKETS = "DELETE FROM Tickets;COMMIT";
    protected static final String DELETE_FROM_COUNTERS = "DELETE FROM Counters;COMMIT";

    protected static ApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

}
