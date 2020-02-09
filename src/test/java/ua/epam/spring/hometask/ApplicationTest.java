package ua.epam.spring.hometask;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.storage.Store;

@RunWith(JUnit4.class)
public class ApplicationTest {

    private static ApplicationContext context;

    @BeforeClass
    public static void setUp() {
        context = new FileSystemXmlApplicationContext(
                "/src/main/resources/config/application-context.xml",
                "/src/main/resources/config/strategies-context.xml");
    }

    @Test
    public void test() {
        Store store = (Store) context.getBean("store");
        DiscountService discountService = (DiscountService) context.getBean("discountService");
    }
}
