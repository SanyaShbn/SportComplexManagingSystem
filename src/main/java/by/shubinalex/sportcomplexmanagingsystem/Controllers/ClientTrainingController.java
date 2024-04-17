package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@RestController
public class ClientTrainingController {
    @Autowired
    private ClientTrainingRepo clientTrainingRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private ClientRepo clientRepo;

    @RequestMapping( value = "/client_trainings", method = RequestMethod.GET)
    public Iterable<ClientTraining> getClientTrainings() {
        return clientTrainingRepo.findAll();
    }

    @RequestMapping(value = "/api/save_client_training", method = RequestMethod.POST)
    public ResponseEntity saveClientTraining(@RequestParam Long trainingId, @RequestParam Long clientId) {
        ClientTraining clientTraining = new ClientTraining();
        Training training = trainingRepo.findById(trainingId).orElseThrow(RuntimeException::new);
        Client client = clientRepo.findById(clientId).orElseThrow(RuntimeException::new);
        for(ClientTraining client_training : clientTrainingRepo.findAll()){
            if(client_training.getClient().getIdClient() == clientId &&
               client_training.getTraining().getIdTraining() == trainingId){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        clientTraining.setTraining(training);
        clientTraining.setClient(client);
        clientTraining.setStatus("запланирована");
        clientTrainingRepo.save(clientTraining);
        training.setClients_amount(training.getClients_amount() + 1);
        trainingRepo.save(training);
        return ResponseEntity.ok(training);
    }

    @RequestMapping(value = "/api/clientTrainings/{id}", method = RequestMethod.PUT)
    public ResponseEntity editClientTraining(@PathVariable Long id, @RequestParam Long trainingId, @RequestParam Long clientId) {
        ClientTraining clientTraining = clientTrainingRepo.findById(id).orElseThrow(RuntimeException::new);
        Training training = trainingRepo.findById(trainingId).orElseThrow(RuntimeException::new);
        Client client = clientRepo.findById(clientId).orElseThrow(RuntimeException::new);
        for(ClientTraining client_training : clientTrainingRepo.findAll()){
            if(client_training.getClient().getIdClient() == clientId &&
                    client_training.getTraining().getIdTraining() == trainingId){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        Training previous_training = clientTraining.getTraining();
        clientTraining.setTraining(training);
        clientTraining.setClient(client);
        clientTrainingRepo.save(clientTraining);
        if(previous_training.getIdTraining() != trainingId) {
            previous_training.setClients_amount(previous_training.getClients_amount() - 1);
            training.setClients_amount(training.getClients_amount() + 1);
            trainingRepo.save(training); trainingRepo.save(previous_training);
        }
        return ResponseEntity.ok(training);
    }

    @RequestMapping(value = "/api/deleteClientTrainings", method = RequestMethod.DELETE)
    public void deleteClientTraining(@RequestParam Long id) {
        ClientTraining clientTraining = clientTrainingRepo.findById(id).orElseThrow(RuntimeException::new);
        Optional<Training> training = trainingRepo.findById(clientTraining.getTraining().getIdTraining());
        Optional<Client> client = clientRepo.findById(clientTraining.getClient().getIdClient());
        training.get().getClientTrainings().remove(clientTraining);
        client.get().getClientTrainings().remove(clientTraining);

        clientTrainingRepo.delete(clientTraining);
        training.get().setClients_amount(training.get().getClients_amount() - 1);
        trainingRepo.save(training.get());
    }
}
