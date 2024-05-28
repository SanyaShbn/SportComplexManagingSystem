package by.shubinalex.sportcomplexmanagingsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    private String start_date;
    private String end_date;
    private String text;
    private String rec_type;
    private Long event_length;
    private String event_pid;
    private String data_type;
    private String description;
}
