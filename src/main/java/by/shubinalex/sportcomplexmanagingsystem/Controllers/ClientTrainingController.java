package by.shubinalex.sportcomplexmanagingsystem.Controllers;

import by.shubinalex.sportcomplexmanagingsystem.entities.*;
import by.shubinalex.sportcomplexmanagingsystem.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
public class ClientTrainingController {
    @Autowired
    private ClientTrainingRepo clientTrainingRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private UserRepo userRepo;

    @RequestMapping( value = "/api/all_client_trainings", method = RequestMethod.GET)
    public Iterable<ClientTraining> getClientTrainings() {
        return clientTrainingRepo.findAll();
    }

    @RequestMapping( value = "/api/coach_client_trainings", method = RequestMethod.GET)
    public Iterable<ClientTraining> getCoachClientTrainings(@RequestParam String userLogin) {
        Optional<User> coach = userRepo.findByEmail(userLogin);
        return StreamSupport.stream(clientTrainingRepo.findAll().spliterator(), false)
                .filter(client_training -> Objects.equals(client_training.getTraining().getCoach().getUserId(), coach.get().getUserId()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/save_client_training", method = RequestMethod.POST)
    public ResponseEntity saveClientTraining(@RequestParam Long trainingId, @RequestParam Long clientId,
                                             @RequestParam int signingsAmount, @RequestParam String userLogin) {
        ClientTraining clientTraining = new ClientTraining();
        boolean isAvailableForSigning = false; int availableVisitsAmount = 0;
        Training training = trainingRepo.findById(trainingId).orElseThrow(RuntimeException::new);
        Optional<User> user = userRepo.findByUserLogin(userLogin);
        if(user.get().getRole() == Role.COACH &&
                !Objects.equals(user.get().getUserId(), training.getCoach().getUserId())){
            return ResponseEntity.status(404).build();
        }
        if(training.getCapacity() == training.getClients_amount()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Client client = clientRepo.findById(clientId).orElseThrow(RuntimeException::new);
        for(ClientTraining client_training : clientTrainingRepo.findAll()){
            if(client_training.getClient().getIdClient() == clientId &&
               client_training.getTraining().getIdTraining() == trainingId){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        for(ClientMembership clientMembership : client.getClientMemberships()){
            for(TrainingMembership trainingMembership: training.getTrainingMemberships()){
                if(clientMembership.getSportComplexMembership() == trainingMembership.getSportComplexMembership()){
                    isAvailableForSigning = true;
                    availableVisitsAmount = trainingMembership.getVisitsAmount();
                    break;
                }
            }
        }
        if(isAvailableForSigning) {
            if(signingsAmount <= availableVisitsAmount) {
                clientTraining.setTraining(training);
                clientTraining.setClient(client);
                clientTraining.setSigningsAmount(signingsAmount);
                clientTrainingRepo.save(clientTraining);
                training.setClients_amount(training.getClients_amount() + 1);
                trainingRepo.save(training);
                return ResponseEntity.ok(training);
            }
            else{return ResponseEntity.status(402).build();}
        }
        else return ResponseEntity.status(401).build();
    }

    @RequestMapping(value = "/api/clientTrainings/{id}", method = RequestMethod.PUT)
    public ResponseEntity editClientTraining(@PathVariable Long id, @RequestParam Long trainingId, @RequestParam Long clientId,
                                             @RequestParam int signingsAmount, @RequestParam String userLogin) {
        ClientTraining clientTraining = clientTrainingRepo.findById(id).orElseThrow(RuntimeException::new);
        boolean isAvailableForSigning = false; int availableVisitsAmount = 0;
        Training training = trainingRepo.findById(trainingId).orElseThrow(RuntimeException::new);
        Optional<User> user = userRepo.findByUserLogin(userLogin);
        if(user.get().getRole() == Role.COACH &&
                !Objects.equals(user.get().getUserId(), training.getCoach().getUserId())){
            return ResponseEntity.status(404).build();
        }
        Client client = clientRepo.findById(clientId).orElseThrow(RuntimeException::new);
        for(ClientMembership clientMembership : client.getClientMemberships()){
            for(TrainingMembership trainingMembership: training.getTrainingMemberships()){
                if(clientMembership.getSportComplexMembership() == trainingMembership.getSportComplexMembership()){
                    isAvailableForSigning = true;
                    availableVisitsAmount = trainingMembership.getVisitsAmount();
                    break;
                }
            }
        }
        if(isAvailableForSigning) {
            if(signingsAmount <= availableVisitsAmount) {
                for (ClientTraining client_training : clientTrainingRepo.findAll()) {
                    if ((Objects.equals(client_training.getClient().getIdClient(), clientId) &&
                            client_training.getTraining().getIdTraining() == trainingId) &&
                            (clientTraining.getTraining().getIdTraining() != trainingId
                                    | !Objects.equals(clientTraining.getClient().getIdClient(), clientId))) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                }
                Training previous_training = clientTraining.getTraining();
                clientTraining.setTraining(training);
                clientTraining.setClient(client);
                clientTraining.setSigningsAmount(signingsAmount);
                if (previous_training.getIdTraining() != trainingId) {
                    if (training.getCapacity() == training.getClients_amount()) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                    previous_training.setClients_amount(previous_training.getClients_amount() - 1);
                    training.setClients_amount(training.getClients_amount() + 1);
                    trainingRepo.save(training);
                    trainingRepo.save(previous_training);
                }
                clientTrainingRepo.save(clientTraining);
                return ResponseEntity.ok(training);
            }
            else{
                return ResponseEntity.status(402).build();
            }
        } else return ResponseEntity.status(401).build();
    }

    @RequestMapping(value = "/api/deleteClientTrainings", method = RequestMethod.DELETE)
    public ResponseEntity deleteClientTraining(@RequestParam Long id, @RequestParam String userLogin) {
        ClientTraining clientTraining = clientTrainingRepo.findById(id).orElseThrow(RuntimeException::new);
        Optional<Training> training = trainingRepo.findById(clientTraining.getTraining().getIdTraining());
        Optional<User> user = userRepo.findByUserLogin(userLogin);
        if(user.get().getRole() == Role.COACH &&
                !Objects.equals(user.get().getUserId(), training.get().getCoach().getUserId())){
            return ResponseEntity.badRequest().build();
        }
        Optional<Client> client = clientRepo.findById(clientTraining.getClient().getIdClient());
        training.get().getClientTrainings().remove(clientTraining);
        client.get().getClientTrainings().remove(clientTraining);

        clientTrainingRepo.delete(clientTraining);
        training.get().setClients_amount(training.get().getClients_amount() - 1);
        trainingRepo.save(training.get());
        return ResponseEntity.ok().build();
    }
}
