package contactManager.dtos.response;

import lombok.Data;

@Data
public class EditContactResponse {
    private String id;
    private String username;
    private String number;
    private String email;
    private String name;
    private String date;
}
