package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.Controllers.TrainingController;
import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainingControllerTest {

    @InjectMocks
    TrainingController trainingController;

    @Mock
    TrainingRepo trainingRepo;

    @Mock
    UserRepo userRepo;

    @Mock
    ComplexFacilityRepo complexFacilityRepo;

    @Mock
    ClientTrainingRepo clientTrainingRepo;

    @Mock
    TrainingMembershipRepo trainingMembershipRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTrainings() {
        trainingController.getTrainings();
        verify(trainingRepo, times(1)).findAll();
    }

    @Test
    public void testSaveTrainingWithComplexFacility() {
        Training training = new Training();
        when(complexFacilityRepo.findById(anyLong())).thenReturn(Optional.of(new ComplexFacility()));
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(new User()));
        ResponseEntity responseEntity = trainingController.saveTraining(training, 1L, 1L);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

//    @Test
//    public void testDeleteTraining() {
//        Training training = new Training();
//        when(trainingRepo.findById(anyLong())).thenReturn(Optional.of(training));
//        ResponseEntity responseEntity = trainingController.deleteTraining(1L);
//        assertEquals(200, responseEntity.getStatusCodeValue());
//    }
}
