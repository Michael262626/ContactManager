package contactManager.dtos.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

}
