package ua.epam.spring.hometask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.service.DiscountService;

@RunWith(JUnit4.class)
public class BaseTest {

    @Test
    public void test() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:application-context.xml", "classpath*:strategies-context.xml");
        DiscountService discountService = (DiscountService) context.getBean("discountService");
    }
}
