package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface ClientMembershipRepo extends CrudRepository<ClientMembership, Long> {
    List<ClientMembership> findByIdClientMembership(@Param("id") Long idClientMembership);

    List<ClientMembership> findByClient(Client client);

    List<ClientMembership> findBySportComplexMembership(SportComplexMembership sportComplexMembership);

}

