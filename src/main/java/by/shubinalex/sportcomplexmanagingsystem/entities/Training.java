package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString
public class Training {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idTraining;
    private String name;
    private Double cost;
    private String type;
    private int capacity;
    private int clients_amount;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private ComplexFacility complexFacility;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User coach;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "training")
    @JsonIgnore
    @ToString.Exclude
    private List<ClientTraining> clientTrainings = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "training")
    @JsonIgnore
    @ToString.Exclude
    private List<TrainingMembership> trainingMemberships = new ArrayList<>();

}
