package contactManager.dtos.request;

import contactManager.data.model.User;
import lombok.Data;

@Data
public class MessageRequest {
    private String message;
    private String sender;
    private String receiver;
}
