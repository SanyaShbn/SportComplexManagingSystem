package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.entities.Client;
import by.shubinalex.sportcomplexmanagingsystem.entities.ClientTraining;
import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.repo.ClientRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class OneToManyRelationTest {

    @Autowired
    ComplexFacilityRepo complexFacilityRepo;
    @Autowired
    TrainingRepo trainingRepo;
    @Autowired
    ClientRepo clientRepo;
    @Test
    public void addTrainingTest() throws Exception {
        ComplexFacility complexFacility = new ComplexFacility();
        Training training = new Training();
        complexFacility.addTraining(training);
    }

    @Test
    public void checkClientTraining() throws Exception {
        Optional<Client> client = clientRepo.findById(202L);
        List<ClientTraining> clientTrainings = client.get().getClientTrainings();
        for(ClientTraining clientTraining : clientTrainings){
            System.out.println(clientTraining.getTraining().getIdTraining());
        }
    }

}
