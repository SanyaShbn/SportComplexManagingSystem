package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer",
        "handler"})
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String userLogin;
    private String userPassword;
    private String firstName;
    private String surName;
    private String patrSurName;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String status;
    private String post;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private LocalDate birthDate;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "coach", fetch=FetchType.LAZY)
    private List<Training> trainings = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "cleaner", fetch=FetchType.LAZY)
    private List<ComplexFacility> complexFacilities = new ArrayList<>();

    public void addTraining(Training training){
        trainings.add(training);
        training.setCoach(this);
    }

    public void addComplexFacility(ComplexFacility complexFacility){
        complexFacilities.add(complexFacility);
        complexFacility.setCleaner(this);
    }
    public void removeTraining(Training training){
        trainings.remove(training);
    }
    public void removeComplexFacility(ComplexFacility complexFacility){
        complexFacilities.remove(complexFacility);
        complexFacility.setCleaner(null);
    }
//    public User(String userLogin, String userPassword, String firstName,
//                String surName, String patrSurName, Role role, String post, String phoneNumber,
//                LocalDate birthDate, double salary, double additionalSalary) {
//        super();
//        this.userLogin = userLogin;
//        this.userPassword = userPassword;
//        this.firstName = firstName;
//        this.surName = surName;
//        this.patrSurName = patrSurName;
//        this.role = role;
//        this.post = post;
//        this.phoneNumber = phoneNumber;
//        this.birthDate = birthDate;
//        this.salary = salary;
//        this.additionalSalary = additionalSalary;
//    }

}