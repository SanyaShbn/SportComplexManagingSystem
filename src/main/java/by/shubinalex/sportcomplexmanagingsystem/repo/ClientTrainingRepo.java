package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.Client;
import by.shubinalex.sportcomplexmanagingsystem.entities.ClientTraining;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientTrainingRepo extends CrudRepository<ClientTraining, Long> {
        List<ClientTraining> findByIdClientTraining(@Param("id") Long idClientTraining);

        List<ClientTraining> findByClient(Client client);

        List<ClientTraining> findByTraining(Training training);

}
