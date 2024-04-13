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
    public Iterable<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @RequestMapping(value = "/api/events", method = RequestMethod.POST)
    public ResponseEntity<Event> addEvents(@RequestBody Event event) {
        if(event.getRec_type().isEmpty()){
            event.setEvent_length(0L);
        }
        eventRepo.save(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/api/events/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@PathVariable("id") long id, @RequestBody Event eventDetails) {
        Optional<Event> eventData = eventRepo.findById(id);

        if (!eventData.get().getRec_type().isEmpty()) {
            eventRepo.deleteByEventPid(Math.toIntExact(eventDetails.getId()));
        }

        if (eventData.isPresent()) {
            Event updated_event = eventData.get();
            updated_event.setText(eventDetails.getText());
            updated_event.setStart_date(eventDetails.getStart_date());
            updated_event.setEnd_date(eventDetails.getEnd_date());
            updated_event.setRec_type(eventDetails.getRec_type());
            updated_event.setEvent_length(eventDetails.getEvent_length());
            if(eventDetails.getRec_type().isEmpty() && !eventData.get().getRec_type().isEmpty()){
                updated_event.setEventPid(Math.toIntExact(eventData.get().getId()));
            }else {
                updated_event.setEventPid(eventDetails.getEventPid());
            }
            return new ResponseEntity<>(eventRepo.save(updated_event), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/events/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") long id) {
        try {
            Optional<Event> eventData = eventRepo.findById(id);
            if(eventData.isPresent()) {
                Event deleted_event = eventData.get();
                if (!deleted_event.getRec_type().isEmpty()) {
                    eventRepo.deleteByEventPid(Math.toIntExact(deleted_event.getId()));
                    eventRepo.deleteById(id);
                }
                else if (deleted_event.getEventPid() != 0) {
                    deleted_event.setRec_type(" ");
                    return new ResponseEntity<>(eventRepo.save(deleted_event), HttpStatus.OK);
                }else {
                    eventRepo.deleteById(id);
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
