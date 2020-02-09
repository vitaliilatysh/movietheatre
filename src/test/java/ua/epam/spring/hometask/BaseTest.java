package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public abstract class BaseTest {

    protected static ApplicationContext context = new FileSystemXmlApplicationContext(
            "/src/main/resources/config/application-context.xml",
            "/src/main/resources/config/strategies-context.xml");

}
