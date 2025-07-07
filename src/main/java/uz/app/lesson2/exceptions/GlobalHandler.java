package uz.app.lesson2.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.app.lesson2.dto.ResponseMessage;


@ControllerAdvice
@ResponseBody
public class GlobalHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemnotFoundException(ItemNotFoundException e) {
        ResponseMessage build = ResponseMessage
                .builder()
                .status(false)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(build);
    }
    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<?> handleUserBadRequestException(ItemNotFoundException e) {
        ResponseMessage build = ResponseMessage
                .builder()
                .status(false)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(build);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        ResponseMessage build = ResponseMessage
                .builder()
                .status(false)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(400).body(build);
    }
}
