package contactManager.dtos.request;

import contactManager.data.model.Contact;
import lombok.Data;

@Data
public class ShareContactRequest {
    private String senderUsername;
    private String recipientUsername;
    private String contactId;
}
