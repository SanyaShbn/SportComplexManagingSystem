package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer",
        "handler"})
public class ComplexFacility {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idComplexFacility;
    private String name;
    private int trainingsAmount;
    private int capacity;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User cleaner;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "complexFacility", fetch=FetchType.LAZY)
    private List<Training> trainings = new ArrayList<>();

    public void addTraining(Training training){
        trainings.add(training);
        training.setComplexFacility(this);
    }

    public void removeTraining(Training training){
        trainings.remove(training);
    }

}
