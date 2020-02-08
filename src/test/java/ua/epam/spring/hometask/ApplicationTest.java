package ua.epam.spring.hometask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.storage.Store;

@RunWith(JUnit4.class)
public class ApplicationTest {

    @Test
    public void test() {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "/src/main/resources/config/application-context.xml",
                "/src/main/resources/config/strategies-context.xml");
        Store store = (Store) context.getBean("store");
        DiscountService discountService = (DiscountService) context.getBean("discountService");
    }
}
