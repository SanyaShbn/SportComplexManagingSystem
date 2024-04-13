package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.Client;
import by.shubinalex.sportcomplexmanagingsystem.entities.ComplexFacility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientRepo extends CrudRepository<Client, Long> {
    List<Client> findByIdClient(@Param("id") Long idClient);

}