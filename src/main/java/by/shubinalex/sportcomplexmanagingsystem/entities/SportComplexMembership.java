package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SportComplexMembership {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idSportComplexMembership;
    @Column(unique = true)
    private String name;
    private LocalDate durationDeadline;
    private Double cost;
    private int completeVisitsAmount;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportComplexMembership")
    @JsonIgnore
    @ToString.Exclude
    private List<TrainingMembership> trainingMemberships = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportComplexMembership")
    @JsonIgnore
    @ToString.Exclude
    private List<ClientMembership> clientMemberships = new ArrayList<>();


}
