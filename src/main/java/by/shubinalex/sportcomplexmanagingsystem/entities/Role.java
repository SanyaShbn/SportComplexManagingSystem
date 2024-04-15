package by.shubinalex.sportcomplexmanagingsystem.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, MANAGER, MARKETER, ACCOUNTANT, COACH, CLEANER;

    @Override
    public String getAuthority() {
        return name();
    }
}
