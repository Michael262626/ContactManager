package contactManager.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReceiveMessageResponse {
    private String message;
    private String sender;
    private LocalDateTime localDateTime = LocalDateTime.now();

}
