package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingMembership {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idTrainingMembership;

    private int visitsAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private SportComplexMembership sportComplexMembership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private Training training;

    public void setSportComplexMembership(SportComplexMembership sportComplexMembership){
        this.sportComplexMembership = sportComplexMembership;
        sportComplexMembership.getTrainingMemberships().add(this);
    }

    public void setTraining(Training training){
        this.training = training;
        training.getTrainingMemberships().add(this);
    }

}
