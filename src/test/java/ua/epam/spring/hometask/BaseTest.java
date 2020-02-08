package ua.epam.spring.hometask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ua.epam.spring.hometask.service.DiscountService;

@RunWith(JUnit4.class)
public class BaseTest {

    @Test
    public void test() {
        ApplicationContext context =
                new FileSystemXmlApplicationContext("/src/main/resources/beans.xml", "/src/main/resources/discount-strategies-beans.xml");
        DiscountService discountService = (DiscountService) context.getBean("discountService");
    }
}
