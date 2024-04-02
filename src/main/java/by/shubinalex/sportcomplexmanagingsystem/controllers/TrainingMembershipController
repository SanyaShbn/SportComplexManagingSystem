package by.shubinalex.sportcomplexmanagingsystem.Controllers;


import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TrainingMembershipController {
    @Autowired
    private TrainingMembershipRepo trainingMembershipRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private SportComplexMembershipRepo sportComplexMembershipRepo;

    @RequestMapping(value = "/training_memberships", method = RequestMethod.GET)
    public Iterable<TrainingMembership> getTrainingMemberships() {
        return trainingMembershipRepo.findAll();
    }

    @RequestMapping(value = "/api/save_training_membership", method = RequestMethod.POST)
    public void saveTrainingMembership(@RequestBody TrainingMembership newTrainingMembership,
        @RequestParam Long trainingId, @RequestParam Long membershipId) {

        TrainingMembership trainingMembership = new TrainingMembership();
        Training training = trainingRepo.findById(trainingId).orElseThrow(RuntimeException::new);
        SportComplexMembership sportComplexMembership = sportComplexMembershipRepo.findById(membershipId).orElseThrow(RuntimeException::new);
        sportComplexMembership.setCompleteVisitsAmount(sportComplexMembership.getCompleteVisitsAmount()
                + newTrainingMembership.getVisitsAmount());
        trainingMembership.setTraining(training);
        trainingMembership.setSportComplexMembership(sportComplexMembership);
        trainingMembership.setVisitsAmount(newTrainingMembership.getVisitsAmount());
        trainingMembershipRepo.save(trainingMembership);
    }

    @RequestMapping(value = "/api/trainingMemberships/{id}", method = RequestMethod.PUT)
    public void editTrainingMembership(@PathVariable Long id, @RequestParam Long trainingId,
                                       @RequestParam Long membershipId, @RequestParam int visitsAmount) {

        TrainingMembership trainingMembership = trainingMembershipRepo.findById(id).orElseThrow(RuntimeException::new);
        Training training = trainingRepo.findById(trainingId).orElseThrow(RuntimeException::new);
        SportComplexMembership sportComplexMembership = sportComplexMembershipRepo.findById(membershipId).orElseThrow(RuntimeException::new);
        if (trainingMembership.getSportComplexMembership() == sportComplexMembership){
        sportComplexMembership.setCompleteVisitsAmount(sportComplexMembership.getCompleteVisitsAmount()
                - trainingMembership.getVisitsAmount() + visitsAmount);
        }
        else{
            SportComplexMembership previous_membership = trainingMembership.getSportComplexMembership();
            previous_membership.setCompleteVisitsAmount(previous_membership.getCompleteVisitsAmount() - trainingMembership.getVisitsAmount());
            sportComplexMembership.setCompleteVisitsAmount(sportComplexMembership.getCompleteVisitsAmount() + visitsAmount);
        }
        trainingMembership.setTraining(training);
        trainingMembership.setSportComplexMembership(sportComplexMembership);
        trainingMembership.setVisitsAmount(visitsAmount);
        trainingMembershipRepo.save(trainingMembership);
    }

    @RequestMapping(value = "/api/deleteTrainingMembership", method = RequestMethod.DELETE)
    public void deleteTrainingMembership(@RequestParam Long id) {
        TrainingMembership trainingMembership = trainingMembershipRepo.findById(id)
                .orElseThrow(RuntimeException::new);
        Optional<Training> training = trainingRepo.findById(trainingMembership.getTraining()
                .getIdTraining());
        Optional<SportComplexMembership> sportComplexMembership = sportComplexMembershipRepo
                .findById(trainingMembership.getSportComplexMembership().getIdSportComplexMembership());
        training.get().getTrainingMemberships().remove(trainingMembership);
        sportComplexMembership.get().getTrainingMemberships().remove(trainingMembership);
        sportComplexMembership.get().setCompleteVisitsAmount(
                sportComplexMembership.get().getCompleteVisitsAmount() - trainingMembership.getVisitsAmount());

        trainingMembershipRepo.delete(trainingMembership);
    }

}
