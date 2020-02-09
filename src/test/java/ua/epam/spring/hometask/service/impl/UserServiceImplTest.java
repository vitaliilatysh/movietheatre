package ua.epam.spring.hometask.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.storage.Store;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UserServiceImplTest {

    private static UserService userService;
    private static User user1;
    private static User user2;
    private static Store store;

    @BeforeClass
    public static void setUp() {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "/src/main/resources/config/application-context.xml",
                "/src/main/resources/config/strategies-context.xml");
        userService = (UserService) context.getBean("userService");
        store = (Store) context.getBean("store");

        user1 = new User();
        user1.setFirstName("Daniel");
        user1.setLastName("Klein");
        user1.setEmail("d.klein@gmail.com");
        user1.setBirthDate(LocalDate.of(1980, Month.DECEMBER, 17));

        user2 = new User();
        user2.setFirstName("Fill");
        user2.setLastName("Krock");
        user2.setEmail("f.krock@gmail.com");
        user2.setBirthDate(LocalDate.of(1985, Month.JANUARY, 5));
    }

    @After
    public void cleanUp() {
        store.getUserMap().clear();
    }

    @Before
    public void saveUser() {
        userService.save(user1);
    }

    @Test
    public void shouldSaveNewUser() {
        assertEquals(user2, userService.save(user2));
    }

    @Test(expected = ItemAlreadyExistException.class)
    public void shouldThrowExceptionUserWithSuchEmailAlreadyRegistered() {
        userService.save(user1);
        userService.save(user1);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldRemoveRegisteredUser() {
        userService.remove(user1);
        userService.getById(user1.getId());
    }

    @Test
    public void shouldReturnUserById() {
        User foundUser = userService.getById(user1.getId());
        assertEquals(foundUser, user1);
    }

    @Test
    public void shouldReturnAllRegisteredUser() {
        userService.save(user2);
        assertEquals(2, userService.getAll().size());
    }
}