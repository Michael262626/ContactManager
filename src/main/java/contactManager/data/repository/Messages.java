package contactManager.data.repository;

import contactManager.data.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Messages extends MongoRepository<Message, String> {
    Message findMessageByMessageId(String sender);

    List<Message> findAllBy(String messageId);
}
