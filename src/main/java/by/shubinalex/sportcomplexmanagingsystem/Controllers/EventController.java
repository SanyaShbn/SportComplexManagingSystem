package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.Event;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EventRepo eventRepo;

    @RequestMapping(value = "/api/events", method = RequestMethod.GET)
    public @ResponseBody Iterable<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @RequestMapping(value = "/api/events", method = RequestMethod.POST)
    public @ResponseBody String addNewEvent (@RequestBody Event event) {
        eventRepo.save(event);
        return "Saved";
    }

    @RequestMapping(value = "/api/events/{id}", method = RequestMethod.PUT)
    public @ResponseBody String updateEvent(@PathVariable("id") Long id, @RequestBody Event eventDetails) {
        Event event = eventRepo.findById(id).get();
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
}
