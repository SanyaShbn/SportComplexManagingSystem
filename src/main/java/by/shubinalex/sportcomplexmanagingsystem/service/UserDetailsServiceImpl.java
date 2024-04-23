package by.shubinalex.sportcomplexmanagingsystem.service;

import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUserLogin(userLogin);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user.isPresent()) {
            User currentUser = user.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(userLogin);
            builder.password(currentUser.getUserPassword());
            builder.roles(String.valueOf(currentUser.getRole()));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }
}
