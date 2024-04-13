package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.EmployeeFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.ServiceEmployee;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.EmployeeFacilityRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.ServiceEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeFacilityController {
    @Autowired
    private EmployeeFacilityRepo employeeFacilityRepo;

    @RequestMapping( "/employee_facilities")
    public Iterable<EmployeeFacility> getEmployeeFacilities() {
        return employeeFacilityRepo.findAll();
    }

}
