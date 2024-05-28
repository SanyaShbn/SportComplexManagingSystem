package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private ClientTrainingRepo clientTrainingRepo;
    @Autowired
    private ClientMembershipRepo clientMembershipRepo;
    @RequestMapping(value = "/api/view_clients", method = RequestMethod.GET)
    public Iterable<Client> getClients() {
        return clientRepo.findAll();
    }

    @RequestMapping(value = "/api/clients/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateClient(@RequestBody Client updated_client, @PathVariable Long id) {
        Client current_client = clientRepo.findById(id).orElseThrow(RuntimeException::new);
        current_client.setFirstName(updated_client.getFirstName());
        current_client.setSurName(updated_client.getSurName());
        current_client.setPatrSurName(updated_client.getPatrSurName());
        current_client.setPhoneNumber(updated_client.getPhoneNumber());
        current_client.setEmail(updated_client.getEmail());
        clientRepo.save(current_client);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/api/clients/{id}", method = RequestMethod.DELETE)
    public void deleteClient(@PathVariable Long id) {

        Client client = clientRepo.findById(id).orElseThrow(RuntimeException::new);
        List<ClientTraining> clientTrainings = clientTrainingRepo.findByClient(client);
        for(ClientTraining delClientTraining : clientTrainings){
            Training updateSignedTraining = delClientTraining.getTraining();
            updateSignedTraining.setClients_amount(
                    updateSignedTraining.getClients_amount() - 1);
            trainingRepo.save(updateSignedTraining);
            clientTrainingRepo.deleteById(delClientTraining.getIdClientTraining());
        }
        List<ClientMembership> clientMemberships = clientMembershipRepo.findByClient(client);
        for(ClientMembership delClientMembership : clientMemberships){
            clientMembershipRepo.deleteById(delClientMembership.getIdClientMembership());
        }
        clientRepo.delete(client);
    }
}
