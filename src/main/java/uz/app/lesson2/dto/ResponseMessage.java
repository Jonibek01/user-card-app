package uz.app.lesson2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String message;
    private Boolean status;
    private Object data;

    public static ResponseMessage success(String message) {
        return ResponseMessage.builder().status(true).message(message).build();
    }

    public static ResponseMessage error(String message) {
        return ResponseMessage.builder().status(false).message(message).build();
    }
}
