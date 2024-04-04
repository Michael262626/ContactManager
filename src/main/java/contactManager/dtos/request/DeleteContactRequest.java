package contactManager.dtos.request;

import lombok.Data;

@Data
public class DeleteContactRequest {
    private String username;
    private String numbers;
}
