package contactManager.dtos.response;

import lombok.Data;

@Data
public class CreateContactResponse {
    private String id;
    private String username;
    private String number;
    private String date;
}
