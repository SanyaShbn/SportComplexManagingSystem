package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import by.shubinalex.sportcomplexmanagingsystem.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableScheduling
@EnableMethodSecurity
public class SportComplexManagingSystemApplication implements CommandLineRunner {

//    @Autowired
//    private ServiceEmployeeRepo serviceEmployeeRepo;
//    @Autowired
//    private ComplexFacilityRepo complexFacilityRepo;
//    @Autowired
//    private TrainingRepo trainingRepo;
//    @Autowired
//    private EmployeeFacilityRepo employeeFacilityRepo;
//    @Autowired
//    private UserRepo userRepo;

    private static final Logger logger =
            LoggerFactory.getLogger(SportComplexManagingSystemApplication.class);

    public static void main(String[] args){

        SpringApplication.run(SportComplexManagingSystemApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
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



//        userRepo.save(new User("admin@gmail.com",
//                "$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW",
//                "Иван", "Смирнов", "Фёдорович", Role.ADMIN,
//                "администратор комплекса", "+375 29 555-45-23", LocalDate.now(),
//                "высшее образование в сфере менеджмента (БГУ)", 3330, 0, 0));
//        userRepo.save(new User("coach_1",
//                "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue",
//                "Степан", "Иванов", "Иванович", Role.COACH,
//                "сотрудник тренерского персонала", "+375 44 151-15-51", LocalDate.now(),
//                "высшее образование в области физической культуры и спорта (БГУ)", 1300, 0, 0));
    }



}
