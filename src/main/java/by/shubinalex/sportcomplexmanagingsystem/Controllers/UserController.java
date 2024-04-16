package by.shubinalex.sportcomplexmanagingsystem.Controllers;
import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/api/account_managing", method = RequestMethod.POST)
    public void updateUserStatus(@RequestBody User user) {
        System.out.println(user);
        Optional<User> updated_status_user = userRepo.findByUserLogin(user.getUserLogin());
        updated_status_user.get().setStatus(user.getStatus());
        userRepo.save(updated_status_user.get());

    }


}
