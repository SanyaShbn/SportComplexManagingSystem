package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.ServiceEmployee;
import by.shubinalex.sportcomplexmanagingsystem.repo.ServiceEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceEmployeeController {
    @Autowired
    private ServiceEmployeeRepo serviceEmployeeRepo;

    @RequestMapping("/service_employees")
    public Iterable<ServiceEmployee> getServiceEmployees() {
        return serviceEmployeeRepo.findAll();
    }
}
