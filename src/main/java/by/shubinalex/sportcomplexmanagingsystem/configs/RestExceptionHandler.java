//package by.shubinalex.sportcomplexmanagingsystem.configs;
//import by.shubinalex.sportcomplexmanagingsystem.dto.ErrorDto;
//import by.shubinalex.sportcomplexmanagingsystem.exceptions.AppException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@ControllerAdvice
//public class RestExceptionHandler {
//
//    @ExceptionHandler(value = { AppException.class })
//    @ResponseBody
//    public ResponseEntity<ErrorDto> handleException(AppException ex) {
//        return ResponseEntity
//                .status(ex.getStatus())
//                .body(ErrorDto.builder().message(ex.getMessage()).build());
//    }
//}