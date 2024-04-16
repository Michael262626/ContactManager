package contactManager.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class GetAllMessage {
    private String content;
    @Id
    private String messageId;
    private String sender;
    private String receiver;
    private String date;
}
