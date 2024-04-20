package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.entities.Event;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setStart_date("2024-04-20");
        event.setEnd_date("2024-04-21");
        event.setText("Test Event");
        event.setRec_type("Test Rec Type");
        event.setEvent_length(60L);
        event.setEvent_pid(1);
        event.setData_type("Test Data Type");
        event.setDescription("Test Description");

        assertEquals(1L, event.getId());
        assertEquals("2024-04-20", event.getStart_date());
        assertEquals("2024-04-21", event.getEnd_date());
        assertEquals("Test Event", event.getText());
        assertEquals("Test Rec Type", event.getRec_type());
        assertEquals(60L, event.getEvent_length());
        assertEquals(1, event.getEvent_pid());
        assertEquals("Test Data Type", event.getData_type());
        assertEquals("Test Description", event.getDescription());
    }
}

