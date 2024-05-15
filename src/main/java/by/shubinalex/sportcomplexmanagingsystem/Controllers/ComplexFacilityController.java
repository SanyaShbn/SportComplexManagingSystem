package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Event;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.EventRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ComplexFacilityController {
    @Autowired
    private ComplexFacilityRepo complexFacilityRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private EventRepo eventRepo;

    @RequestMapping(value = "/api/view_facilities",method = RequestMethod.GET)
    public Iterable<ComplexFacility> getComplexFacilities() {
        return complexFacilityRepo.findAll();
    }
    @RequestMapping(value = "/api/complexFacilities/{id}",method = RequestMethod.DELETE)
    public void deleteComplexFacilities(@PathVariable("id") Long id) {
        ComplexFacility deleting_facility = complexFacilityRepo.findById(id).get();
        for(Training training: deleting_facility.getTrainings()){
            Training updated_training = trainingRepo.findById(training.getIdTraining()).get();
            updated_training.setComplexFacility(null);
            trainingRepo.save(updated_training);
        }

        eventRepo.delete(eventRepo.findByText("Уборка и обслуживание. " + deleting_facility.getName() + " №"
                + deleting_facility.getIdComplexFacility()).get());
        complexFacilityRepo.delete(deleting_facility);
    }
    @RequestMapping(value = "/api/complexFacilities/{id}",method = RequestMethod.PUT)
    public void updateComplexFacilities(@RequestBody ComplexFacility complex_facility,  @PathVariable Long id) {
        ComplexFacility updating_facility = complexFacilityRepo.findById(id).get();
        for(Training training: updating_facility.getTrainings()){
            Training updated_training = trainingRepo.findById(training.getIdTraining()).get();
            updated_training.setComplexFacility(null);
            trainingRepo.save(updated_training);
        }
        eventRepo.delete(eventRepo.findByText("Уборка и обслуживание. " + updating_facility.getName() + " №"
                + updating_facility.getIdComplexFacility()).get());
        updating_facility.setName(complex_facility.getName());
        updating_facility.setCapacity(complex_facility.getCapacity());
        updating_facility.setTrainingsAmount(0);
        updating_facility.setTrainings(null);
       complexFacilityRepo.save(updating_facility);
    }

}
