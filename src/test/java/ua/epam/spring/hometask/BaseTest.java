package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.AppConfig;

public abstract class BaseTest {

    protected static ApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

}
