package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.entities.ServiceEmployee;
import by.shubinalex.sportcomplexmanagingsystem.repo.ServiceEmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class SportComplexManagingSystemApplication implements CommandLineRunner {

    @Autowired
    private ServiceEmployeeRepo repository;

    private static final Logger logger =
            LoggerFactory.getLogger(SportComplexManagingSystemApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(SportComplexManagingSystemApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

//        ServiceEmployee serviceEmployee_1 = new ServiceEmployee("Алекс", "Шубин", "Алексеевич",
//                "+375 44 111-22-33", LocalDate.now(), 5000, 0);
//        ServiceEmployee serviceEmployee_2 = new ServiceEmployee("Виктор", "Грицков", "Виктрыч",
//                "+375 44 666-66-66", LocalDate.now(), 1000000, 0);
//        ServiceEmployee serviceEmployee_3 = new ServiceEmployee("Женёк", "Шостак", "Женкович",
//                "+375 29 123-45-67", LocalDate.now(), 2330, 100);
//        repository.saveAll(Arrays.asList(serviceEmployee_1, serviceEmployee_2, serviceEmployee_3));
//
//        for (ServiceEmployee serviceEmployee : repository.findAll()) {
//            logger.info(serviceEmployee.getFirstName() + " " + serviceEmployee.getSurName()
//            + " " + serviceEmployee.getPatrSurName());
//        }

    }



}
