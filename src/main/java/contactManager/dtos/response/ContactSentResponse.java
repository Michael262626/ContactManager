package contactManager.dtos.response;

import lombok.Data;

@Data
public class ContactSentResponse {
    private String contactId;
    private String contactName;
    private String contactPhoneNumber;
    private String contactEmail;
    private String date;
}
