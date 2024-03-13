package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComplexFacilityController {
    @Autowired
    private ComplexFacilityRepo complexFacilityRepo;

    @RequestMapping(value = "/api/view_facilities",method = RequestMethod.GET)
    public Iterable<ComplexFacility> getComplexFacilities() {
        return complexFacilityRepo.findAll();
    }

}
