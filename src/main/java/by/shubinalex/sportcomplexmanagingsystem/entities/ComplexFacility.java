package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idComplexFacility;
    private String facilityType;
    private int trainingsAmount;
    private LocalDateTime cleaningServiceTime;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "complexFacility", fetch=FetchType.LAZY)
    private List<Training> trainings = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "complexFacility")
    private List<EmployeeFacility> employeeFacilities = new ArrayList<>();

    public ComplexFacility(String facilityType, int trainingsAmount, LocalDateTime cleaningServiceTime) {
        super();
        this.facilityType = facilityType;
        this.trainingsAmount = trainingsAmount;
        this.cleaningServiceTime = cleaningServiceTime;
    }

    public void addTraining(Training training){
        trainings.add(training);
        training.setComplexFacility(this);
    }

    public void removeTraining(Training training){
        trainings.remove(training);
    }

}
