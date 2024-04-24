package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.AccountCredentials;
import by.shubinalex.sportcomplexmanagingsystem.entities.Role;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import by.shubinalex.sportcomplexmanagingsystem.service.JwtService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        UsernamePasswordAuthenticationToken creds =
                new UsernamePasswordAuthenticationToken(
                        credentials.getUserLogin(),
                        credentials.getUserPassword());


        Authentication auth = authenticationManager.authenticate(creds);

        List<Role> authorities = auth.getAuthorities().stream()
                .map(authority -> Role.valueOf(authority.getAuthority().substring(5, authority.getAuthority().length())))
                .collect(Collectors.toList());

        List<String> roles = authorities.stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList());

        String status = userRepo.findByUserLogin(credentials.getUserLogin()).get().getStatus();
        // Generate token
        String jwts = jwtService.getToken(auth.getName(), roles, status);

        // Build response with the generated token
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .build();

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody AccountCredentials credentials) {
        Optional<User> user = userRepo.findByEmail(credentials.getUserLogin());
        User registered_user = user.get();
        String password = new BCryptPasswordEncoder().encode(credentials.getUserPassword());
        registered_user.setUserLogin(credentials.getUserLogin());
        registered_user.setUserPassword(password);
        registered_user.setStatus("active");

        if (userRepo.findByUserLogin(credentials.getUserLogin()).isEmpty()) {
            userRepo.save(registered_user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @RequestMapping(value = "/update_admin_profile", method = RequestMethod.POST)
    public ResponseEntity<?> updateAdminProfile(@RequestBody User user) {
        try {
            User updated_admin = userRepo.findByUserLogin(user.getEmail()).get();
            updated_admin.setFirstName(user.getFirstName());
            updated_admin.setSurName(user.getSurName());
            updated_admin.setPatrSurName(user.getPatrSurName());
            updated_admin.setPhoneNumber(user.getPhoneNumber());
            if (user.getUserPassword() != null) {
                if(!user.getUserPassword().isEmpty()) {
                    String password = new BCryptPasswordEncoder().encode(user.getUserPassword());
                    updated_admin.setUserPassword(password);
                }
            }
            updated_admin.setStatus("active");
            if (!updated_admin.getUserLogin().equals(user.getUserLogin()) &&
                    userRepo.findByUserLogin(user.getUserLogin()).isEmpty()) {
                updated_admin.setEmail(user.getUserLogin());
                updated_admin.setUserLogin(user.getUserLogin());
            }
            userRepo.save(updated_admin);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}