package by.shubinalex.sportcomplexmanagingsystem.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCredentials {
    private String userLogin;
    private String userPassword;
    @Enumerated(EnumType.STRING)
    private Role role;
}
