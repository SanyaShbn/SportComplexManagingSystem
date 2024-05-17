package by.shubinalex.sportcomplexmanagingsystem.Controllers;
import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Role;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @RequestMapping(value = "/api/account_managing", method = RequestMethod.POST)
    public void updateUserStatus(@RequestBody User user) {
        System.out.println(user);
        Optional<User> updated_status_user = userRepo.findByUserLogin(user.getUserLogin());
        updated_status_user.get().setStatus(user.getStatus());
        userRepo.save(updated_status_user.get());
    }
    @RequestMapping(value = "/api/view_coaches",method = RequestMethod.GET)
    public Iterable<User> getCoaches() {
        return userRepo.findByRole(Role.valueOf(Role.COACH.getAuthority()));
    }

    @RequestMapping(value = "/api/view_cleaners",method = RequestMethod.GET)
    public Iterable<User> getCleaners() {
        return userRepo.findByRole(Role.valueOf(Role.CLEANER.getAuthority()));
    }

    @RequestMapping(value = "/api/user_profile",method = RequestMethod.GET)
    public Optional<User> getUser(@RequestParam String userLogin) {
        return userRepo.findByUserLogin(userLogin);
    }
    @RequestMapping(value = "/api/delete_coach",method = RequestMethod.DELETE)
    public void deleteCoach(@RequestParam Long coach_id) {
        User deleting_coach = userRepo.findById(coach_id).get();
        for(Training training: deleting_coach.getTrainings()){
            Training updated_training = trainingRepo.findById(training.getIdTraining()).get();
            updated_training.setCoach(null);
            trainingRepo.save(updated_training);
        }
        userRepo.deleteById(coach_id);
    }

}