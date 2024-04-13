package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.SportComplexMembership;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SportComplexMembershipRepo extends CrudRepository<SportComplexMembership, Long> {
    List<SportComplexMembership> findByIdSportComplexMembership(@Param("id") Long idSportComplexMembership);

}