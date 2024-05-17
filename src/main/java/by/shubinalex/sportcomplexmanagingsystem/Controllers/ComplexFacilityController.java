package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Event;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.EventRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ComplexFacilityController {
    @Autowired
    private ComplexFacilityRepo complexFacilityRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/api/view_facilities",method = RequestMethod.GET)
    public Iterable<ComplexFacility> getComplexFacilities() {
        return complexFacilityRepo.findAll();
    }

    @RequestMapping(value = "/api/view_cleaned_facilities",method = RequestMethod.GET)
    public Iterable<ComplexFacility> getCleanedComplexFacilities() {
        return StreamSupport.stream(complexFacilityRepo.findAll().spliterator(), false)
                .filter(facility -> facility.getCleaner() != null)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/save_complex_facility", method = RequestMethod.POST)
    public void saveComplexFacility(@RequestBody ComplexFacility complexFacility, @RequestParam Long cleanerId) {

        Optional<User> optionalUser = userRepo.findById(cleanerId);

        if(optionalUser.isPresent()) {
            User cleaner = optionalUser .get();
            cleaner.addComplexFacility(complexFacility);
        }
        complexFacilityRepo.save(complexFacility);
    }
    @RequestMapping(value = "/api/complexFacilities/{id}",method = RequestMethod.DELETE)
    public void deleteComplexFacilities(@PathVariable("id") Long id) {
        ComplexFacility deleting_facility = complexFacilityRepo.findById(id).get();
        for(Training training: deleting_facility.getTrainings()){
            Training updated_training = trainingRepo.findById(training.getIdTraining()).get();
            updated_training.setComplexFacility(null);
            trainingRepo.save(updated_training);
        }
        if(deleting_facility.getCleaner() != null) {
            Optional<User> cleaner = Optional.of(deleting_facility.getCleaner());
            cleaner.get().removeComplexFacility(deleting_facility);
        }
        List<Event> deletingEvents = eventRepo.findByText("Уборка и обслуживание. " + deleting_facility.getName() + " №"
                + deleting_facility.getIdComplexFacility());
        eventRepo.deleteAll(deletingEvents);
        complexFacilityRepo.delete(deleting_facility);
    }
    @RequestMapping(value = "/api/complexFacilities/{id}",method = RequestMethod.PUT)
    public ResponseEntity updateComplexFacilities(@RequestBody ArrayList<String> complex_facility, @PathVariable Long id,
                                                  @RequestParam Long cleanerId) {
        ComplexFacility updating_facility = complexFacilityRepo.findById(id).get();
        User previous_cleaner = updating_facility.getCleaner();
        if(previous_cleaner != null){
            previous_cleaner.removeComplexFacility(updating_facility);
        }
        for(Training training: updating_facility.getTrainings()){
            Training updated_training = trainingRepo.findById(training.getIdTraining()).get();
            updated_training.setComplexFacility(null);
            trainingRepo.save(updated_training);
        }
        List<Event> deletingEvents = eventRepo.findByText("Уборка и обслуживание. " + updating_facility.getName() + " №"
                + updating_facility.getIdComplexFacility());
        eventRepo.deleteAll(deletingEvents);
        updating_facility.setName(complex_facility.get(0));
        updating_facility.setCapacity(Integer.parseInt(complex_facility.get(1)));
        updating_facility.setTrainingsAmount(0);
        updating_facility.setTrainings(null);
        try{
            Optional<User> optionalUser = userRepo.findById(cleanerId);
            if(optionalUser.isPresent()){
                User cleaner = optionalUser.get();
                cleaner.addComplexFacility(updating_facility);
            }
            complexFacilityRepo.save(updating_facility);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
