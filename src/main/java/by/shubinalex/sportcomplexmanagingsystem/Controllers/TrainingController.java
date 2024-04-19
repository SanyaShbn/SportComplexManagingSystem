package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TrainingController {

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private UserRepo userRepo;

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
//
//    @GetMapping("/{id}")
//    public Training getTraining(@PathVariable Long idTraining) {
//        return trainingRepo.findById(idTraining).orElseThrow(RuntimeException::new);
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity updateTraining(@PathVariable Long idTraining, @RequestBody Training training) {
//        Training currentTraining = trainingRepo.findById(idTraining).orElseThrow(RuntimeException::new);
//        currentTraining.setTrainingDateTime(training.getTrainingDateTime());
//        currentTraining .setCost(training.getCost());
//        currentTraining  = trainingRepo.save(training);
//
//        return ResponseEntity.ok(currentTraining);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteTraining(@PathVariable Long idTraining) {
//        trainingRepo.deleteById( idTraining);
//        return ResponseEntity.ok().build();
//    }
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
        trainingRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/api/trainings/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateTraining(@RequestBody Training updated_training, @PathVariable Long id, @RequestParam Long complexFacilityId,
                                         @RequestParam Long userId) {
        Training current_training = trainingRepo.findById(id).orElseThrow(RuntimeException::new);
        current_training.setCost(updated_training.getCost());
        current_training.setName(updated_training.getName());
        current_training.setCapacity(updated_training.getCapacity());
        current_training.setType(updated_training.getType());

        Optional<ComplexFacility> complexFacilityOptional = complexFacilityRepo.findById(complexFacilityId);
        Optional<User> user = userRepo.findById(userId);

        if(complexFacilityOptional.isPresent() && user.isPresent()) {
            ComplexFacility complexFacility = complexFacilityOptional.get();
            User coach = user.get();

            Optional<ComplexFacility> updatedPreviousComplexFacility = complexFacilityRepo.findById(current_training.getComplexFacility().getIdComplexFacility());
            Optional<User> updatedPreviousCoach = userRepo.findById(current_training.getCoach().getUserId());
            updatedPreviousComplexFacility.get().setTrainingsAmount(updatedPreviousComplexFacility.get().getTrainingsAmount() - 1);
            updatedPreviousComplexFacility.get().removeTraining(current_training);
            updatedPreviousCoach.get().removeTraining(current_training);

            complexFacility.addTraining(current_training);
            coach.addTraining(current_training);
            complexFacility.setTrainingsAmount(complexFacility.getTrainingsAmount() + 1);

            trainingRepo.save(current_training);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
