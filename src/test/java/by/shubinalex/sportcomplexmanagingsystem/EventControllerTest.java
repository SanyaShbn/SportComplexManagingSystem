package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.Controllers.EventController;
import by.shubinalex.sportcomplexmanagingsystem.entities.Event;
import by.shubinalex.sportcomplexmanagingsystem.repo.EventRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventControllerTest {

    @InjectMocks
    EventController eventController;

    @Mock
    EventRepo eventRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEvents() {
        eventController.getAllEvents();
        verify(eventRepo, times(1)).findAll();
    }

    @Test
    public void testAddNewEvent() {
        Event event = new Event();
        when(eventRepo.save(any(Event.class))).thenReturn(event);
        String response = eventController.addNewEvent(event);
        assertEquals("Saved", response);
    }

    @Test
    public void testUpdateEvent() {
        Event event = new Event();
        when(eventRepo.findById(anyLong())).thenReturn(Optional.of(event));
        when(eventRepo.save(any(Event.class))).thenReturn(event);
        String response = eventController.updateEvent(1L, event, "alex_sh@gmail.com");
        assertEquals("Updated", response);
    }

    @Test
    public void testDeleteEvent() {
        doNothing().when(eventRepo).deleteById(anyLong());
        String response = eventController.deleteEvent(1L);
        assertEquals("Deleted", response);
    }
}
