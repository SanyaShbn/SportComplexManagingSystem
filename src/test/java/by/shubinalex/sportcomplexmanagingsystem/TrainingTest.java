package by.shubinalex.sportcomplexmanagingsystem;
import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TrainingTest {

    @InjectMocks
    Training training;

    @Mock
    ComplexFacility complexFacility;

    @Mock
    User coach;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIdTraining() {
        training.setIdTraining(1L);
        assertEquals(1L, training.getIdTraining());
    }

    @Test
    public void testName() {
        training.setName("Пробная тренировка");
        assertEquals("Пробная тренировка", training.getName());
    }
    @Test
    public void testCost() {
        training.setCost(Double.valueOf(50));
        assertEquals(Double.valueOf(50), training.getCost());
    }

    @Test
    public void testCapacity() {
        training.setCapacity(10);
        assertEquals(10, training.getCapacity());
    }

    @Test
    public void testType() {
        training.setType("групповая");
        assertEquals("групповая", training.getType());
    }

    @Test
    public void testClientsAmount() {
        training.setClients_amount(5);
        assertEquals(5, training.getClients_amount());
    }
    @Test
    public void testComplexFacility() {
        when(complexFacility.getIdComplexFacility()).thenReturn(1L);
        training.setComplexFacility(complexFacility);
        assertEquals(1L, training.getComplexFacility().getIdComplexFacility());
    }

    @Test
    public void testCoach() {
        when(coach.getUserId()).thenReturn(1L);
//        training.setCoach(coach);
//        assertEquals(1L, training.getCoach().getUserId());
    }

}