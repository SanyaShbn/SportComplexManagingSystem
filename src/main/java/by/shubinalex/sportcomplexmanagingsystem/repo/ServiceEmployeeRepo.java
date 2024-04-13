package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.ServiceEmployee;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface ServiceEmployeeRepo extends CrudRepository<ServiceEmployee, Long> {
    List<ServiceEmployee> findByIdServiceEmployee(@Param("id") Long idServiceEmployee);

}
