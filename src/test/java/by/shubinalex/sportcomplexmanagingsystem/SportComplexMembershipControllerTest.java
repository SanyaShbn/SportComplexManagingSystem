package by.shubinalex.sportcomplexmanagingsystem;
import by.shubinalex.sportcomplexmanagingsystem.Controllers.SportComplexMembershipController;
import by.shubinalex.sportcomplexmanagingsystem.entities.SportComplexMembership;
import by.shubinalex.sportcomplexmanagingsystem.repo.ClientMembershipRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.SportComplexMembershipRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingMembershipRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class SportComplexMembershipControllerTest {

    @InjectMocks
    private SportComplexMembershipController sportComplexMembershipController;

    @Mock
    private SportComplexMembershipRepo sportComplexMembershipRepo;

    @Mock
    private TrainingMembershipRepo trainingMembershipRepo;

    @Mock
    private ClientMembershipRepo clientMembershipRepo;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sportComplexMembershipController).build();
    }

    @Test
    public void testGetMemberships() throws Exception {
        mockMvc.perform(get("/api/view_memberships"))
                .andExpect(status().isOk());

        verify(sportComplexMembershipRepo, times(1)).findAll();
    }

    @Test
    public void testAddSportComplexMembership() throws Exception {
        SportComplexMembership sportComplexMembership = new SportComplexMembership();
        sportComplexMembership.setIdSportComplexMembership(1L);
        sportComplexMembership.setName("Пробный абонемент");
        sportComplexMembership.setCost(100.0);
        sportComplexMembership.setDurationDeadline(LocalDate.now());
        sportComplexMembershipRepo.save(sportComplexMembership);
    }
    @Test
    public void testDeleteSportComplexMembership() throws Exception {
        SportComplexMembership sportComplexMembership = new SportComplexMembership();
        sportComplexMembership.setIdSportComplexMembership(1L);

        when(sportComplexMembershipRepo.findById(anyLong())).thenReturn(Optional.of(sportComplexMembership));

        mockMvc.perform(delete("/api/sportComplexMemberships/{id}", 1L))
                .andExpect(status().isOk());

        verify(sportComplexMembershipRepo, times(1)).findById(anyLong());
        verify(clientMembershipRepo, times(1)).findBySportComplexMembership(any(SportComplexMembership.class));
        verify(trainingMembershipRepo, times(1)).findBySportComplexMembership(any(SportComplexMembership.class));
        verify(sportComplexMembershipRepo, times(1)).delete(any(SportComplexMembership.class));
    }

    @Test
    public void testUpdateSportComplexMembership() throws Exception {
        SportComplexMembership sportComplexMembership = new SportComplexMembership();
        sportComplexMembership.setIdSportComplexMembership(1L);
        sportComplexMembership.setName("Пробный абонемент");
        sportComplexMembership.setCost(100.0);

        when(sportComplexMembershipRepo.findById(anyLong())).thenReturn(Optional.of(sportComplexMembership));

        mockMvc.perform(put("/api/sportComplexMemberships/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sportComplexMembership)))
                .andExpect(status().isOk());

        verify(sportComplexMembershipRepo, times(1)).findById(anyLong());
        verify(sportComplexMembershipRepo, times(1)).save(any(SportComplexMembership.class));
    }
}

