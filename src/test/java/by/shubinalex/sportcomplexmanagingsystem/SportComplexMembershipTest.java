package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.entities.SportComplexMembership;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SportComplexMembershipTest {
    @Test
    public void test() {
        SportComplexMembership sportComplexMembership = new SportComplexMembership();
        sportComplexMembership.setIdSportComplexMembership(1L);
        sportComplexMembership.setName("Пробный абонемент");
        sportComplexMembership.setCost(Double.valueOf(50));
        sportComplexMembership.setCompleteVisitsAmount(0);
        sportComplexMembership.setDurationDeadline(LocalDate.now());

        assertEquals(1L, sportComplexMembership.getIdSportComplexMembership());
        assertEquals("Пробный абонемент", sportComplexMembership.getName());
        assertEquals(50, sportComplexMembership.getCost());
        assertEquals(0, sportComplexMembership.getCompleteVisitsAmount());
        assertEquals(LocalDate.now(), sportComplexMembership.getDurationDeadline());
    }
}
