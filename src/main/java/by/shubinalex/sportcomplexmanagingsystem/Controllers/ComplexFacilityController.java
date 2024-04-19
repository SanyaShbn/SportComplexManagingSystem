package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComplexFacilityController {
    @Autowired
    private ComplexFacilityRepo complexFacilityRepo;
    @Autowired
    private TrainingRepo trainingRepo;

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
        complexFacilityRepo.delete(deleting_facility);
    }

}
