package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.Event;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.repo.EventRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private TrainingRepo trainingRepo;

    @RequestMapping(value = "/api/events", method = RequestMethod.GET)
    public @ResponseBody Iterable<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @RequestMapping(value = "/api/events", method = RequestMethod.POST)
    public @ResponseBody String addNewEvent (@RequestBody Event event) {
        if(isEventDurationOverlapping(event)) {
            return "Event duration is overlapping with other events. Cannot save.";
        }
        eventRepo.save(event);
        return "Saved";
    }

    @RequestMapping(value = "/api/events/{id}", method = RequestMethod.PUT)
    public @ResponseBody String updateEvent(@PathVariable("id") Long id, @RequestBody Event eventDetails) {
        Optional<Event> optionalEvent = eventRepo.findById(id);
        Event event;
        event = optionalEvent.orElseGet(() -> eventRepo.findByText(eventDetails.getText()).get());
        if(isEventDurationOverlapping(eventDetails)) {
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
    public @ResponseBody String deleteEvent(@PathVariable("id") Long id) {
        eventRepo.deleteById(id);
        return "Deleted";
    }

    private boolean isEventDurationOverlapping(Event event) {
        Iterable<Event> allEvents = eventRepo.findAll();
        Long event_id = Long.parseLong(event.getText().replaceAll("\\D+", ""));
        Optional<Training> training = trainingRepo.findById(event_id);

        Instant instant_start_date = Instant.parse(event.getStart_date());
        Instant instant_end_date = Instant.parse(event.getEnd_date());
        LocalDateTime eventStartDate = instant_start_date.atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime eventEndDate = instant_end_date.atZone(ZoneId.systemDefault()).toLocalDateTime();

        for (Event e : allEvents) {
            if (!Objects.equals(e.getId(), event_id)) {
                Instant other_instant_start_date = Instant.parse(e.getStart_date());
                Instant other_instant_end_date = Instant.parse(e.getEnd_date());
                LocalDateTime otherEventStartDate = other_instant_start_date.atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime otherEventEndDate = other_instant_end_date .atZone(ZoneId.systemDefault()).toLocalDateTime();
                Long other_event_id = Long.parseLong(e.getText().replaceAll("\\D+", ""));
                Optional<Training> other_training = trainingRepo.findById(other_event_id);
                if ((eventStartDate.isEqual(otherEventStartDate) || eventEndDate.isEqual(otherEventEndDate)) ||
                (eventEndDate.isEqual(otherEventStartDate) || eventStartDate.isEqual(otherEventEndDate)) ||
                        (eventEndDate.isAfter(otherEventStartDate) && eventStartDate.isBefore(otherEventEndDate))) {
                    if(training.isPresent() && other_training.isPresent()) {
                        if (training.get().getType().equals("групповое") &&
                                (training.get().getComplexFacility().equals(other_training.get().getComplexFacility())
                                        || training.get().getCoach().equals(other_training.get().getCoach()))) {
                            return true;
                        } else if (training.get().getType().equals("персональное") &&
                                training.get().getCoach().equals(other_training.get().getCoach())) {
                            return true;
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
                    }
                }
            }
        }
        return false;
    }
}
