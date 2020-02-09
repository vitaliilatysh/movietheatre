package ua.epam.spring.hometask.service.impl;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.service.AuditoriumService;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class AuditoriumServiceImplTest {

    private static AuditoriumService auditoriumService;
    private static Auditorium auditorium;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "/src/main/resources/config/application-context.xml",
                "/src/main/resources/config/strategies-context.xml");
        auditoriumService = (AuditoriumService) context.getBean("auditoriumService");

        auditorium = new Auditorium();
        auditorium.setName("KinoPlaneta");
    }

    @Test
    public void shouldReturnAllAuditoriumsInitializedFromPropertiesFile() {
        assertEquals(3, auditoriumService.getAll().size());
    }

    @Test
    public void shouldThrowExceptionIfAuditoriumWithSuchNameNotFound() {
        expectedEx.expect(ItemNotFoundException.class);
        expectedEx.expectMessage("Auditorium not found by name " + auditorium.getName());
        Auditorium foundAuditorium = auditoriumService.getByName(auditorium.getName());
        assertNull(foundAuditorium);
    }

    @Test
    public void getByName() {
        assertNotNull(auditoriumService.getByName("Kyiv"));
    }
}