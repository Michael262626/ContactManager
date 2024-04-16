package contactManager.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class GetMessageResponse {
    private String message;
    private String messageId;
    private String date;
}
