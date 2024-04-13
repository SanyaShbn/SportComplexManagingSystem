package by.shubinalex.sportcomplexmanagingsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Builder.Default
    @OneToMany(mappedBy = "serviceEmployee")
    private List<EmployeeFacility> employeeFacilities = new ArrayList<>();

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
