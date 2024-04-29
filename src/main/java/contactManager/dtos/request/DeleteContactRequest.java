package contactManager.dtos.request;

import lombok.Data;

@Data
public class DeleteContactRequest {
    private String numbers;
    private String name;
}
