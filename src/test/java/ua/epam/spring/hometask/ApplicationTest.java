package ua.epam.spring.hometask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.storage.Store;

@RunWith(JUnit4.class)
public class ApplicationTest extends BaseTest {

    @Test
    public void test() {
        Store store = (Store) context.getBean("store");
        DiscountService discountService = (DiscountService) context.getBean("discountService");
    }
}
