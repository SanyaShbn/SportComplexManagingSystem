package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class ClientMembershipController {
    @Autowired
    private ClientMembershipRepo clientMembershipRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private SportComplexMembershipRepo sportComplexMembershipRepo;

    @RequestMapping(value = "/client_memberships", method = RequestMethod.GET)
    public Iterable<ClientMembership> getClientMemberships() {
        return clientMembershipRepo.findAll();
    }

    @RequestMapping(value = "/api/save_client_membership", method = RequestMethod.POST)
    public void saveClientMembership(@RequestParam Long membershipId, @RequestParam Long clientId) {

        ClientMembership clientMembership = new ClientMembership();
        Client client = clientRepo.findById(clientId).orElseThrow(RuntimeException::new);
        SportComplexMembership sportComplexMembership = sportComplexMembershipRepo.findById(membershipId).orElseThrow(RuntimeException::new);
        clientMembership.setClient(client);
        clientMembership.setSportComplexMembership(sportComplexMembership);
        clientMembership.setSoldAt(LocalDate.now());
        clientMembership.setRevenue(sportComplexMembership.getCost());
        clientMembershipRepo.save(clientMembership);
    }

    @RequestMapping(value = "/api/clientMemberships/{id}", method = RequestMethod.PUT)
    public void editClientMembership(@PathVariable Long id, @RequestParam Long membershipId, @RequestParam Long clientId) {
            ClientMembership clientMembership = clientMembershipRepo.findById(id).orElseThrow(RuntimeException::new);
            Client client = clientRepo.findById(clientId).orElseThrow(RuntimeException::new);
            SportComplexMembership sportComplexMembership = sportComplexMembershipRepo.findById(membershipId).orElseThrow(RuntimeException::new);
            clientMembership.setSportComplexMembership(sportComplexMembership);
            clientMembership.setClient(client);
            clientMembership.setSoldAt(LocalDate.now());
            clientMembership.setRevenue(sportComplexMembership.getCost());
            clientMembershipRepo.save(clientMembership);
    }
    @RequestMapping(value = "/api/deleteClientMemberships", method = RequestMethod.DELETE)
    public void deleteClientMembership(@RequestParam Long id) {
        ClientMembership clientMembership = clientMembershipRepo.findById(id).orElseThrow(RuntimeException::new);
        Optional<SportComplexMembership> sportComplexMembership = sportComplexMembershipRepo
                .findById(clientMembership.getSportComplexMembership().getIdSportComplexMembership());
        Optional<Client> client = clientRepo.findById(clientMembership.getClient().getIdClient());
        sportComplexMembership.get().getClientMemberships().remove(clientMembership);
        client.get().getClientMemberships().remove(clientMembership);

        clientMembershipRepo.delete(clientMembership);
    }
}
