package by.shubinalex.sportcomplexmanagingsystem.Controllers;
import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import by.shubinalex.sportcomplexmanagingsystem.entities.Role;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
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
    @Autowired
    private ComplexFacilityRepo complexFacilityRepo;
    @RequestMapping(value = "/api/account_managing", method = RequestMethod.POST)
    public void updateUserStatus(@RequestBody User user) {
        Optional<User> updated_status_user = userRepo.findByUserLogin(user.getEmail());
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

    @RequestMapping(value = "/api/update_coach_or_cleaner",method = RequestMethod.PUT)
    public void updateUser(@RequestBody User updatedUser, @RequestParam Long employee_id) {
        User updatingEmployee = userRepo.findById(employee_id).get();
        updatingEmployee.setFirstName(updatedUser.getFirstName());
        updatingEmployee.setSurName(updatedUser.getSurName());
        updatingEmployee.setPatrSurName(updatedUser.getPatrSurName());
        updatingEmployee.setEmail(updatedUser.getEmail());
        updatingEmployee.setStatus(updatedUser.getStatus());
        updatingEmployee.setPhoneNumber(updatedUser.getPhoneNumber());
        updatingEmployee.setUserLogin(updatedUser.getUserLogin());
        updatingEmployee.setUserPassword(updatedUser.getUserPassword());
        updatingEmployee.setBirthDate(updatedUser.getBirthDate());

        if(updatingEmployee.getRole() == Role.COACH && updatedUser.getRole() != Role.COACH){
            for (Training training : updatingEmployee.getTrainings()) {
                Training updatedTraining = trainingRepo.findById(training.getIdTraining()).get();
                updatedTraining.setCoach(null);
                trainingRepo.save(updatedTraining);
            }
        }
        if(updatingEmployee.getRole() == Role.CLEANER && updatedUser.getRole() != Role.CLEANER){
            for (ComplexFacility complexFacility : updatingEmployee.getComplexFacilities()) {
                ComplexFacility updatedComplexFacility =
                        complexFacilityRepo.findById(complexFacility.getIdComplexFacility()).get();
                updatedComplexFacility.setCleaner(null);
                complexFacilityRepo.save(updatedComplexFacility);
            }
        }
        updatingEmployee.setPost(updatedUser.getPost());
        updatingEmployee.setRole(updatedUser.getRole());
        userRepo.save(updatingEmployee);
    }
    @RequestMapping(value = "/api/delete_coach_or_cleaner",method = RequestMethod.DELETE)
    public void deleteCoachOrCleaner(@RequestParam Long employee_id) {
        User deletingEmployee = userRepo.findById(employee_id).get();
        if(deletingEmployee.getRole() == Role.COACH) {
            for (Training training : deletingEmployee.getTrainings()) {
                Training updatedTraining = trainingRepo.findById(training.getIdTraining()).get();
                updatedTraining.setCoach(null);
                trainingRepo.save(updatedTraining);
            }
        }else{
            for (ComplexFacility complexFacility : deletingEmployee.getComplexFacilities()) {
                ComplexFacility updatedComplexFacility = complexFacilityRepo.findById(
                        complexFacility.getIdComplexFacility()).get();
                updatedComplexFacility.setCleaner(null);
                complexFacilityRepo.save(updatedComplexFacility);
            }
        }
        userRepo.deleteById(employee_id);
    }

}