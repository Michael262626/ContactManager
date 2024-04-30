package contactManager.dtos.response;

import lombok.Data;

@Data
public class GetAllContactResponse {
    private String name;
    private String phoneNumber;
    private String email;
    private String username;
    private String time;
}
