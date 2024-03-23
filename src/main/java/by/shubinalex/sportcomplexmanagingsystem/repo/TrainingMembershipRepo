package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.ClientTraining;
import by.shubinalex.sportcomplexmanagingsystem.entities.SportComplexMembership;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.entities.TrainingMembership;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TrainingMembershipRepo extends CrudRepository<TrainingMembership, Long> {
    List<TrainingMembership> findByIdTrainingMembership(@Param("id") Long idTrainingMembership);

    List<TrainingMembership> findByTraining(Training training);

    List<TrainingMembership> findBySportComplexMembership(SportComplexMembership sportComplexMembership);

}
