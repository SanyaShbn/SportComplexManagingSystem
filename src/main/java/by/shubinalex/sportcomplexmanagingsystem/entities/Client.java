package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable=false, updatable=false)
    private Long idClient;
    private String firstName;
    private String surName;
    private String patrSurName;
    private LocalDate birthDate;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String email;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    @JsonIgnore
    @ToString.Exclude
    private List<ClientTraining> clientTrainings = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    @JsonIgnore
    @ToString.Exclude
    private List<ClientMembership> clientMemberships = new ArrayList<>();
}
