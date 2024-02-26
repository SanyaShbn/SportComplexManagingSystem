package by.shubinalex.sportcomplexmanagingsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
public class ServiceEmployee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idServiceEmployee;
    private String firstName;
    private String surName;
    private String patrSurName;
    private String phoneNumber;
    private LocalDate birthDate;
    private double salary;
    private double additionalSalary;

    public ServiceEmployee() {}

    public ServiceEmployee(String firstName, String surName, String patrSurName,
               String phoneNumber, LocalDate birthDate, double salary, double additionalSalary) {
        super();
        this.firstName = firstName;
        this.surName = surName;
        this.patrSurName = patrSurName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.salary = salary;
        this.additionalSalary = additionalSalary;
    }

}
