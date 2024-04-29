package contactManager.dtos.request;

import lombok.Data;

@Data
public class CreateContactRequest {
    private String email;
    private String number;
    private String name;
}
