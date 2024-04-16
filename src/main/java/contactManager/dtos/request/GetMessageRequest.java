package contactManager.dtos.request;

import contactManager.data.model.User;
import lombok.Data;

@Data
public class GetMessageRequest {
    private String sender;
}
