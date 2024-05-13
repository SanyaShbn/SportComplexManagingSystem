//package by.shubinalex.sportcomplexmanagingsystem.service;
//
//import by.shubinalex.sportcomplexmanagingsystem.entities.Client;
//import by.shubinalex.sportcomplexmanagingsystem.repo.ClientRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import com.ibm.icu.text.Transliterator;
//
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.Scanner;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Service
//public class ClientService {
//    @Autowired
//    private ClientRepo clientRepo;
//
//    public void generateClients(int count) {
//
//    }
//
//    public String generateName(String gender) throws IOException {
//        String name;
//        if(gender.equals("male")) {
//            List<String> result = Files.readAllLines(Paths.get("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\names_male.txt"));
//            name = result.get(ThreadLocalRandom.current().nextInt(0, result.size() - 1));
//        }
//        else{
//            List<String> result = Files.readAllLines(Paths.get("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\names_female.txt"));
//            name = result.get(ThreadLocalRandom.current().nextInt(0, result.size() - 1));
//        }
//
//        return name;
//    }
//
//    public String generateSurname(String gender) throws IOException {
//
//        String sur_name;
//        if(gender.equals("male")) {
//            List<String> result = Files.readAllLines(Paths.get("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\sur_names_male.txt"));
//            sur_name = result.get(ThreadLocalRandom.current().nextInt(0, result.size() - 1));
//        }
//        else{
//            List<String> result = Files.readAllLines(Paths.get("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\sur_names_female.txt"));
//            sur_name = result.get(ThreadLocalRandom.current().nextInt(0, result.size() - 1));
//        }
//
//        return sur_name;
//    }
//
//    public String generatePatrSurname(String gender) throws IOException {
//
//        String patr_sur_name;
//        if(gender.equals("male")) {
//            List<String> result = Files.readAllLines(Paths.get("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\patr_sur_name_male.txt"));
//            patr_sur_name = result.get(ThreadLocalRandom.current().nextInt(0, result.size() - 1));
//        }
//        else{
//            List<String> result = Files.readAllLines(Paths.get("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\patr_sur_name_female.txt"));
//            patr_sur_name = result.get(ThreadLocalRandom.current().nextInt(0, result.size() - 1));
//        }
//
//        return patr_sur_name;
//    }
//
//    public String generatePhoneNumber() throws IOException {
//
//        String phone_number = " ";
//        File phoneNumbersFile = new File("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\phone_numbers.txt");
//        try {
//            Scanner scan = new Scanner(phoneNumbersFile);
//            ArrayList<String> listOfPhoneNumbers = new ArrayList<>();
//            while (scan.hasNext()) {
//                listOfPhoneNumbers.add(scan.nextLine());
//            }
//            int index = (int) (Math.random() * listOfPhoneNumbers.size());
//            phone_number = listOfPhoneNumbers.get(index);
//            listOfPhoneNumbers.remove(phone_number);
//            FileWriter fileWriter = new FileWriter("D:\\Уник\\SportComplexManagingSystem\\src\\main\\resources\\static\\clients_db_data\\phone_numbers.txt");
//                for (String phone_numb : listOfPhoneNumbers) {
//                    fileWriter.append(phone_numb).append("\n");
//                    fileWriter.flush();
//                }
//
//        } catch (FileNotFoundException e) {
//        }
//
//        return phone_number;
//    }
//
//    public String generateEmail(String name, String surname, String domain) throws IOException {
//
//        String email;
//        String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
//        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
//        String name_latin = toLatinTrans.transliterate(name.toLowerCase());
//        String sur_name_latin = toLatinTrans.transliterate(surname.toLowerCase());
//
//        email = sur_name_latin + "_" + name_latin + "@" + domain;
//
//        return email;
//    }
//
//    @Scheduled(initialDelay = 600000, fixedRate = 600000)
//    public void generateClients() throws IOException {
//        String name, surname, patr_surname;
//        Client client = new Client();
//        String[] genders = {"male", "female"};
//        String[] domains = {"gmail.com", "yandex.by", "mail.ru", "outlook.com"};
//
//        String gender = genders[new Random().nextInt(genders.length)];
//        String domain = domains[new Random().nextInt(domains.length)];
//
//        name = generateName(gender);
//        surname = generateSurname(gender);
//        patr_surname = generatePatrSurname(gender);
//
//        client.setFirstName(name); client.setSurName(surname);
//        client.setPatrSurName(patr_surname); client.setPhoneNumber(generatePhoneNumber());
//        client.setEmail(generateEmail(name, surname, domain));
//
//        clientRepo.save(client);
//
//    }
//}
