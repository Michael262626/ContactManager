package contactManager.dtos.request;

import lombok.Data;

@Data
public class EditContactRequest {
    private String newUsername;
    private String newNumber;
    private String newEmailAddress;
}
