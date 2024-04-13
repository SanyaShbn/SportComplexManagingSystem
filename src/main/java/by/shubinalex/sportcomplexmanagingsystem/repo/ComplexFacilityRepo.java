package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ComplexFacilityRepo  extends CrudRepository<ComplexFacility, Long> {
    List<ComplexFacility> findByIdComplexFacility(@Param("id") Long idComplexFacility);

}
