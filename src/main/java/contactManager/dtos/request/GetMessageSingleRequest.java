package contactManager.dtos.request;

import lombok.Data;

@Data
public class GetMessageSingleRequest {
    private String sender;
    private String messageId;
}
