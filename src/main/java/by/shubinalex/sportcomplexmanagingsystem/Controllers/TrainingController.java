package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TrainingController {

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private ComplexFacilityRepo complexFacilityRepo;

    @Autowired
    private ClientTrainingRepo clientTrainingRepo;

    @Autowired
    private TrainingMembershipRepo trainingMembershipRepo;

    @RequestMapping(value = "/api/view_trainings", method = RequestMethod.GET)
    public Iterable<Training> getTrainings() {
        return trainingRepo.findAll();
    }

    @RequestMapping(value = "/api/save_trainings", method = RequestMethod.POST)
    public ResponseEntity saveTrainingWithComplexFacility(@RequestBody Training training, @RequestParam Long complexFacilityId,
                                                @RequestParam Long userId) {

        Optional<ComplexFacility> complexFacilityOptional = complexFacilityRepo.findById(complexFacilityId);
        Optional<User> user = userRepo.findById(userId);

        if(complexFacilityOptional.isPresent() && user.isPresent()) {
            ComplexFacility complexFacility = complexFacilityOptional.get();
            User coach = user.get();

            complexFacility.addTraining(training);
            coach.addTraining(training);
            complexFacility.setTrainingsAmount(complexFacility.getTrainingsAmount() + 1);
            trainingRepo.save(training);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/api/trainings/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTraining(@PathVariable Long id) {
        Optional<Training> training = trainingRepo.findById(id);
        if(training.get().getComplexFacility() != null) {
            Optional<ComplexFacility> updatedComplexFacility = complexFacilityRepo.findById(training.get().getComplexFacility().getIdComplexFacility());
            updatedComplexFacility.get().setTrainingsAmount(updatedComplexFacility.get().getTrainingsAmount() - 1);
            updatedComplexFacility.get().removeTraining(training.get());
        }
        if(training.get().getCoach() != null) {
            Optional<User> coach = Optional.of(training.get().getCoach());
            coach.get().removeTraining(training.get());
        }

        Training delTraining = trainingRepo.findById(id).orElseThrow(RuntimeException::new);
        List<ClientTraining> clientTrainings = clientTrainingRepo.findByTraining(delTraining);
        for(ClientTraining delClientTraining : clientTrainings){
            clientTrainingRepo.deleteById(delClientTraining.getIdClientTraining());
        }
        List<TrainingMembership> trainingMemberships = trainingMembershipRepo.findByTraining(delTraining);
        for(TrainingMembership delTrainingMembership : trainingMemberships){
            SportComplexMembership sportComplexMembership = delTrainingMembership.getSportComplexMembership();
            sportComplexMembership.setCompleteVisitsAmount(sportComplexMembership.getCompleteVisitsAmount() - delTrainingMembership.getVisitsAmount());
            trainingMembershipRepo.deleteById(delTrainingMembership.getIdTrainingMembership());
        }
        List<Event> events = eventRepo.findByText("Тренировка №" + training.get().getIdTraining());
        eventRepo.deleteAll(events);
        trainingRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/api/trainings/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateTraining(@RequestBody ArrayList<String> updated_training, @PathVariable Long id, @RequestParam Long complexFacilityId,
                                         @RequestParam Long userId) {
        Training current_training = trainingRepo.findById(id).orElseThrow(RuntimeException::new);
        current_training.setCost(Double.valueOf(updated_training.get(0)));
        current_training.setName(updated_training.get(1));
        current_training.setCapacity(Integer.parseInt(updated_training.get(2)));
        current_training.setType(updated_training.get(3));
        try{
        User coach = userRepo.findById(userId).orElseThrow(RuntimeException::new);
        ComplexFacility complexFacility = complexFacilityRepo.findById(complexFacilityId).orElseThrow(RuntimeException::new);

        if(current_training.getComplexFacility() != null) {
            Optional<ComplexFacility> updatedPreviousComplexFacility = complexFacilityRepo.findById(current_training.getComplexFacility().getIdComplexFacility());
            updatedPreviousComplexFacility.get().setTrainingsAmount(updatedPreviousComplexFacility.get().getTrainingsAmount() - 1);
            updatedPreviousComplexFacility.get().removeTraining(current_training);
        }
        if(current_training.getCoach() != null) {
            Optional<User> updatedPreviousCoach = userRepo.findById(current_training.getCoach().getUserId());
            updatedPreviousCoach.get().removeTraining(current_training);
        }
        complexFacility.addTraining(current_training);
        coach.addTraining(current_training);
        complexFacility.setTrainingsAmount(complexFacility.getTrainingsAmount() + 1);
        trainingRepo.save(current_training);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
