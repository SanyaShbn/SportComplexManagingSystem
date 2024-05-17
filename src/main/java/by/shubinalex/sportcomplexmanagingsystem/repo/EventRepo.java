package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.Event;
import by.shubinalex.sportcomplexmanagingsystem.entities.Training;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EventRepo extends CrudRepository<Event, Long> {
    List<Event> findByText(String text);
}
