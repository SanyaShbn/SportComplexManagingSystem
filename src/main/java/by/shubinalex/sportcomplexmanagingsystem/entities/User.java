package by.shubinalex.sportcomplexmanagingsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false, updatable=false)
    private Long userId;

    @Column(nullable=false, unique=true)
    private String userLogin;

    @Column(nullable=false)
    private String userPassword;

    private String firstName;
    private String surName;
    private String patrSurName;

//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name="user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Role role;

    private String post;
    private String phoneNumber;
    private LocalDate birthDate;
    private String education;
    private double salary;
    private double additionalSalary;
    private Integer workExperience;

    public User() {
    }

    public User(String userLogin, String userPassword, String firstName,
                String surName, String patrSurName, Role role, String post, String phoneNumber,
                LocalDate birthDate, String education, double salary, double additionalSalary,
                Integer workExperience) {
        super();
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.surName = surName;
        this.patrSurName = patrSurName;
        this.role = role;
        this.post = post;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.education = education;
        this.salary = salary;
        this.additionalSalary = additionalSalary;
        this.workExperience = workExperience;
    }

}
