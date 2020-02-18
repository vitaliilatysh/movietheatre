package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.AppConfig;

public abstract class BaseTest {

    public static final String DELETE_FROM_EVENTS = "DELETE FROM Events";
    public static final String DELETE_FROM_USERS = "DELETE FROM Users";
    public static final String DELETE_FROM_DISCOUNTS = "DELETE FROM Discounts";

    protected static ApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

}
