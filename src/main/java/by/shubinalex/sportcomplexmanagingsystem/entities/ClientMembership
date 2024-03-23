package by.shubinalex.sportcomplexmanagingsystem.entities;

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
public class ClientMembership {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idClientMembership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private SportComplexMembership sportComplexMembership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public void setSportComplexMembership(SportComplexMembership sportComplexMembership){
        this.sportComplexMembership = sportComplexMembership;
        sportComplexMembership.getClientMemberships().add(this);
    }

    public void setClient(Client client){
        this.client = client;
        client.getClientMemberships().add(this);
    }

}

