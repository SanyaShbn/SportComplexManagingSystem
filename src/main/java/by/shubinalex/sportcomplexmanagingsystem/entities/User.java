package by.shubinalex.sportcomplexmanagingsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String userLogin;
    private String userPassword;
    private String firstName;
    private String surName;
    private String patrSurName;

//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name="user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Role role;
    private String status;
    private String post;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private LocalDate birthDate;
    private double salary;
    private double additionalSalary;
    public User(String userLogin, String userPassword, String firstName,
                String surName, String patrSurName, Role role, String post, String phoneNumber,
                LocalDate birthDate, double salary, double additionalSalary) {
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
        this.salary = salary;
        this.additionalSalary = additionalSalary;
    }

}
