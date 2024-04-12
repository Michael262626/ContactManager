package contactManager.dtos.request;

import lombok.Data;

@Data
public class CreateContactRequest {
    private String username;
    private String email;
    private String number;
}
