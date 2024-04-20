package by.shubinalex.sportcomplexmanagingsystem;

import by.shubinalex.sportcomplexmanagingsystem.Controllers.UserController;
import by.shubinalex.sportcomplexmanagingsystem.entities.Role;
import by.shubinalex.sportcomplexmanagingsystem.entities.User;
import by.shubinalex.sportcomplexmanagingsystem.repo.TrainingRepo;
import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepo userRepo;

    @Mock
    private TrainingRepo trainingRepo;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testUpdateUserStatus() throws Exception {
        User user = new User();
        user.setUserLogin("test_user@gmail.com");
        user.setStatus("active");
        user.setEmail(user.getEmail());
        user.setPhoneNumber("+375293968767");

        when(userRepo.findByUserLogin(anyString())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/account_managing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userRepo, times(1)).findByUserLogin(anyString());
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testGetCoaches() throws Exception {
        mockMvc.perform(get("/api/view_coaches"))
                .andExpect(status().isOk());

        verify(userRepo, times(1)).findByRole(any(Role.class));
    }

    @Test
    public void testDeleteFiredEmployee() {
        User user = new User();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        userRepo.deleteById(user.getUserId());
    }

    @Test
    public void testDeleteCoach() throws Exception {
        User coach = new User();
        coach.setUserId(1L);
        coach.setRole(Role.COACH);
        coach.setTrainings(new ArrayList<>());

        when(userRepo.findById(anyLong())).thenReturn(Optional.of(coach));

        mockMvc.perform(delete("/api/delete_coach")
                        .param("coach_id", "1"))
                .andExpect(status().isOk());

        verify(userRepo, times(1)).findById(anyLong());
        verify(userRepo, times(1)).deleteById(anyLong());
    }
}

