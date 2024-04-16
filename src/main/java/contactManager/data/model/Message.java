package contactManager.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document("messages")
public class Message {
    private String content;
    @Id
    private String messageId;
    private String sender;
    private String receiver;
    private LocalDateTime localDateTime = LocalDateTime.now();
}
