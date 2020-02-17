package ua.epam.spring.hometask.service.impl;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UserServiceImplTest extends BaseTest {

    private static UserService userService;
    private static User user1;
    private static User user2;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        userService = context.getBean(UserService.class);

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
        userService.remove(user1);
    }

    @Before
    public void saveUser() {
        user1 = userService.save(user1);
    }

    @Test
    public void shouldSaveNewUser() {
        assertEquals(user2, userService.save(user2));
    }


    @Test
    public void shouldReturnByEmail() {
        assertEquals(user1, userService.getUserByEmail(user1.getEmail()));
    }

    @Test
    public void shouldThrowExceptionIfUserNotFoundWithSuchEmail() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("User not found by email" + user2.getEmail());

        userService.getUserByEmail(user2.getEmail());
    }

    @Test
    public void shouldThrowExceptionUserWithSuchEmailAlreadyRegistered() {
        expectedEx.expect(ItemAlreadyExistException.class);
        expectedEx.expectMessage("User already registered");

        userService.save(user1);
    }

    @Test
    public void shouldRemoveRegisteredUser() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("User not found by id: " + user1.getId());

        userService.remove(user1);
        userService.getById(user1.getId());
    }

    @Test
    public void shouldThrowExceptionIfRegisteredUserNotFound() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("User not found by id: " + user2.getId());

        userService.remove(user2);
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