package by.shubinalex.sportcomplexmanagingsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeFacility {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idEmployeeFacility;

    @ManyToOne
    @JoinColumn(name = "service_employee_id")
    private ServiceEmployee serviceEmployee;

    @ManyToOne
    @JoinColumn(name = "complex_facility_id")
    private ComplexFacility complexFacility;

    public void setServiceEmployee(ServiceEmployee serviceEmployee){
        this.serviceEmployee = serviceEmployee;
        serviceEmployee.getEmployeeFacilities().add(this);
    }

    public void setComplexFacility(ComplexFacility complexFacility){
        this.complexFacility = complexFacility;
        complexFacility.getEmployeeFacilities().add(this);
    }

    public EmployeeFacility(ServiceEmployee serviceEmployee, ComplexFacility complexFacility) {
        super();
        this.serviceEmployee = serviceEmployee;
        this.complexFacility = complexFacility;

    }
}
