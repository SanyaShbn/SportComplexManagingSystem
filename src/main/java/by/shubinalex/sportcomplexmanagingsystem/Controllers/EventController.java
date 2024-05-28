package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.ComplexFacilityRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.EventRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class EventController {

    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ComplexFacilityRepo complexFacilityRepo;

    @RequestMapping(value = "/api/events", method = RequestMethod.GET)
    public @ResponseBody Iterable<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @RequestMapping(value = "/api/coach_events", method = RequestMethod.GET)
    public Iterable<Event> getCoachEvents(@RequestParam String userLogin) {
        Optional<User> coach = userRepo.findByEmail(userLogin);
        if (coach.isPresent()) {
            List<Long> coachTrainingIds = coach.get().getTrainings().stream()
                    .map(Training::getIdTraining)
                    .toList();

            return StreamSupport.stream(eventRepo.findAll().spliterator(), false)
                    .filter(event -> coachTrainingIds.contains(Long.parseLong(event.getText().replaceAll("\\D+", ""))))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @RequestMapping(value = "/api/events", method = RequestMethod.POST)
    public @ResponseBody String addNewEvent (@RequestBody Event event, @RequestParam boolean confirmOverlapping) {
        if(isEventDurationOverlapping(event.getId(), event, confirmOverlapping)) {
            return "Event duration is overlapping with other events. Cannot save.";
        }
        eventRepo.save(event);
        return "Saved";
    }

    @RequestMapping(value = "/api/events/{id}", method = RequestMethod.PUT)
    public @ResponseBody String updateEvent(@PathVariable("id") String id, @RequestBody Event eventDetails,
                                            @RequestParam String userLogin, @RequestParam boolean confirmOverlapping) {
        Event event = eventRepo.findById(id).orElseThrow(RuntimeException::new);
        Optional<User> user = userRepo.findByUserLogin(userLogin);
        if(user.isPresent()){
            if(user.get().getRole() == Role.COACH){
                boolean is_available_for_update = false;
                Long training_id = Long.parseLong(event.getText().replaceAll("\\D+", ""));
                Optional<Training> updated_training = trainingRepo.findById(training_id);
                if(updated_training.isPresent()) {
                    for (Training training : user.get().getTrainings()) {
                        if (training.getIdTraining() == training_id) {
                            is_available_for_update = true;
                            break;
                        }
                    }
                    if (!is_available_for_update) {
                        return "Access denied!";
                    }
                }else{return "Access denied!";}
            }

        }
        if(isEventDurationOverlapping(event.getId(), eventDetails, confirmOverlapping)) {
            return "Event duration is overlapping with other events. Cannot save.";
        }
        event.setText(eventDetails.getText());
        event.setStart_date(eventDetails.getStart_date());
        event.setEnd_date(eventDetails.getEnd_date());
        event.setRec_type(eventDetails.getRec_type());
        event.setDescription(eventDetails.getDescription());
        event.setData_type(eventDetails.getData_type());
        event.setEvent_length(eventDetails.getEvent_length());
        event.setEvent_pid(eventDetails.getEvent_pid());
        eventRepo.save(event);
        return "Updated";
    }

    @RequestMapping(value = "/api/events/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteEvent(@PathVariable("id") String id, @RequestParam String userLogin) {
        Event event = eventRepo.findById(id).orElseThrow(RuntimeException::new);
        Optional<User> user = userRepo.findByUserLogin(userLogin);
        if(user.isPresent()){
            if(user.get().getRole() == Role.COACH){
                boolean is_available_for_update = false;
                Long training_id = Long.parseLong(event.getText().replaceAll("\\D+", ""));
                Optional<Training> deleting_training = trainingRepo.findById(training_id);
                if(deleting_training.isPresent()) {
                    for (Training training : user.get().getTrainings()) {
                        if (training.getIdTraining() == training_id) {
                            is_available_for_update = true;
                            break;
                        }
                    }
                    if (!is_available_for_update) {
                        return "Access denied!";
                    }
                }else{return "Access denied!";}
            }

        }
        eventRepo.delete(event);
        return "Deleted";
    }

    private boolean isEventDurationOverlapping(String id, Event event, boolean confirmOverlapping) {
        Iterable<Event> allEvents = eventRepo.findAll();
        Long event_id = Long.parseLong(event.getText().replaceAll("\\D+", ""));
        Optional<Training> training = trainingRepo.findById(event_id);
        Instant instant_start_date = Instant.parse(event.getStart_date());
        Instant instant_end_date = Instant.parse(event.getEnd_date());
        LocalDateTime eventStartDate = instant_start_date.atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime eventEndDate = instant_end_date.atZone(ZoneId.systemDefault()).toLocalDateTime();

        for (Event e : allEvents) {
            if (!Objects.equals(e.getId(), id)) {
                Instant other_instant_start_date = Instant.parse(e.getStart_date());
                Instant other_instant_end_date = Instant.parse(e.getEnd_date());
                LocalDateTime otherEventStartDate = other_instant_start_date.atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime otherEventEndDate = other_instant_end_date .atZone(ZoneId.systemDefault()).toLocalDateTime();
                Long other_event_id = Long.parseLong(e.getText().replaceAll("\\D+", ""));
                Optional<Training> other_training = trainingRepo.findById(other_event_id);
                if (((eventStartDate.isEqual(otherEventStartDate) || eventEndDate.isEqual(otherEventEndDate)) ||
                (eventEndDate.isEqual(otherEventStartDate) || eventStartDate.isEqual(otherEventEndDate)) ||
                        (eventEndDate.isAfter(otherEventStartDate) && eventStartDate.isBefore(otherEventEndDate))) && confirmOverlapping) {
                    if(training.isPresent() && other_training.isPresent()) {
                        if (training.get().getType().equals("групповое") &&
                                (training.get().getComplexFacility().equals(other_training.get().getComplexFacility())
                                        || training.get().getCoach().equals(other_training.get().getCoach()))) {
                            return true;
                        } else if (training.get().getType().equals("персональное")) {
                            if(other_training.get().getType().equals("персональное") &&
                                    training.get().getCoach().equals(other_training.get().getCoach())){
                                return true;
                            }
                            if(other_training.get().getType().equals("групповое") &&
                                    (training.get().getComplexFacility().equals(other_training.get().getComplexFacility())
                                    || training.get().getCoach().equals(other_training.get().getCoach()))) {
                                return true;
                            }
                        }
                    }
                    else if(training.isPresent() || other_training.isPresent()){
                        if(training.isPresent()){
                            if(training.get().getComplexFacility().getIdComplexFacility() ==
                                    Long.parseLong(e.getText().replaceAll("\\D+", ""))){
                                return true;
                            }
                        }
                        if(other_training.isPresent()){
                            if(other_training.get().getComplexFacility().getIdComplexFacility() ==
                                    event_id){
                                return true;
                            }
                        }
                    }else{
                        Optional<ComplexFacility> complexFacility = complexFacilityRepo.findById(event_id);
                        Optional<ComplexFacility> otherComplexFacility = complexFacilityRepo.findById(other_event_id);
                        if(complexFacility.isPresent() && otherComplexFacility.isPresent()){
                            if(complexFacility.get().getCleaner().equals(otherComplexFacility.get().getCleaner())){
                                return true;
                            }
                        }

                    }
                }
            }
        }
        return false;
    }
}
