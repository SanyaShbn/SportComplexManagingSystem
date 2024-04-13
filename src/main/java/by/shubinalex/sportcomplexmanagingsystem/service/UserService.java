//package by.shubinalex.sportcomplexmanagingsystem.service;
//
//import by.shubinalex.sportcomplexmanagingsystem.dto.CredentialsDto;
//import by.shubinalex.sportcomplexmanagingsystem.dto.SignUpDto;
//import by.shubinalex.sportcomplexmanagingsystem.dto.UserDto;
//import by.shubinalex.sportcomplexmanagingsystem.entities.User;
//import by.shubinalex.sportcomplexmanagingsystem.exceptions.AppException;
//import by.shubinalex.sportcomplexmanagingsystem.mappers.UserMapper;
//import by.shubinalex.sportcomplexmanagingsystem.repo.UserRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.nio.CharBuffer;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//
//    private final UserRepo userRepo;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final UserMapper userMapper;
//
//    public UserDto login(CredentialsDto credentialsDto) {
//        User user = userRepo.findByUserLogin(credentialsDto.getUserLogin())
//                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
//
//        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getUserPassword()), user.getUserPassword())) {
//            return userMapper.toUserDto(user);
//        }
//        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
//    }
//
//    public UserDto register(SignUpDto userDto) {
//        Optional<User> optionalUser = userRepo.findByUserLogin(userDto.getUserLogin());
//
//        if (optionalUser.isPresent()) {
//            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
//        }
//
//        User user = userMapper.signUpToUser(userDto);
//        user.setUserPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getUserPassword())));
//
//        User savedUser = userRepo.save(user);
//
//        return userMapper.toUserDto(savedUser);
//    }
//
//    public UserDto findByLogin(String login) {
//        User user = userRepo.findByUserLogin(login)
//                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
//        return userMapper.toUserDto(user);
//    }
//
//}