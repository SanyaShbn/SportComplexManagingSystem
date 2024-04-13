package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SportComplexMembershipController {
    @Autowired
    private SportComplexMembershipRepo sportComplexMembershipRepo;

    @Autowired
    private TrainingMembershipRepo trainingMembershipRepo;

    @Autowired
    private ClientMembershipRepo clientMembershipRepo;
    @RequestMapping(value = "/api/view_memberships", method = RequestMethod.GET)
    public Iterable<SportComplexMembership> getMemberships() {

        return sportComplexMembershipRepo.findAll();
    }

    @RequestMapping(value = "/api/sportComplexMemberships/{id}", method = RequestMethod.DELETE)
    public void deleteSportComplexMembership(@PathVariable Long id) {

        SportComplexMembership sportComplexMembership = sportComplexMembershipRepo
                .findById(id).orElseThrow(RuntimeException::new);
        List<ClientMembership> clientMemberships = clientMembershipRepo.findBySportComplexMembership(sportComplexMembership);
        for(ClientMembership delClientMembership : clientMemberships){
            clientMembershipRepo.deleteById(delClientMembership.getIdClientMembership());
        }
        List<TrainingMembership> trainingMemberships = trainingMembershipRepo.findBySportComplexMembership(sportComplexMembership);
        for(TrainingMembership delTrainingMembership : trainingMemberships){
            SportComplexMembership updatedSportComplexMembership = delTrainingMembership.getSportComplexMembership();
            updatedSportComplexMembership.setCompleteVisitsAmount(updatedSportComplexMembership.getCompleteVisitsAmount() - delTrainingMembership.getVisitsAmount());
            trainingMembershipRepo.deleteById(delTrainingMembership.getIdTrainingMembership());
        }
        sportComplexMembershipRepo.delete(sportComplexMembership);
    }

    @RequestMapping(value = "/api/sportComplexMemberships/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateSportComplexMembership(@RequestBody SportComplexMembership updatedSportComplexMembership, @PathVariable Long id) {
        SportComplexMembership sportComplexMembership
                = sportComplexMembershipRepo.findById(id).orElseThrow(RuntimeException::new);
        sportComplexMembership.setName(updatedSportComplexMembership.getName());
        sportComplexMembership.setCost(updatedSportComplexMembership.getCost());
        sportComplexMembership.setDurationDeadline(updatedSportComplexMembership.getDurationDeadline());

        sportComplexMembershipRepo.save(sportComplexMembership);

        return ResponseEntity.ok().build();
    }
}
