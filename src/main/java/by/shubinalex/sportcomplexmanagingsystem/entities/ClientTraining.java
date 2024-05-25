package by.shubinalex.sportcomplexmanagingsystem.entities;

import com.sun.jna.platform.unix.solaris.LibKstat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientTraining {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idClientTraining;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private Training training;

    public void setClient(Client client){
        this.client = client;
        client.getClientTrainings().add(this);
    }

    public void setTraining(Training training){
        this.training = training;
        training.getClientTrainings().add(this);
    }

}
