package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class SportComplexManagingSystemApplication{

    private static final Logger logger =
            LoggerFactory.getLogger(SportComplexManagingSystemApplication.class);

    public static void main(String[] args){

        SpringApplication.run(SportComplexManagingSystemApplication.class, args);

    }

}
