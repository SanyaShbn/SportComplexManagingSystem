package by.shubinalex.sportcomplexmanagingsystem.repo;

import by.shubinalex.sportcomplexmanagingsystem.entities.Role;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepo extends CrudRepository<User, Long> {
        List<User> findByUserId(@Param("id") Long userId);
        Optional<User> findByUserLogin(String userLogin);
        Optional<User> findByEmail(String email);
        List<User> findByRole(Role role);
}