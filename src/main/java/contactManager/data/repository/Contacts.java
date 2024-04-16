package contactManager.data.repository;

import contactManager.data.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Contacts extends MongoRepository<Contact, String> {
    Contact findByUsername(String username);

    Contact findContactBy(String username);

    List<Contact> findContactsBy(String username);
}
