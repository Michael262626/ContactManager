package contactManager.data.repository;

import contactManager.data.model.Contact;
import contactManager.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Contacts extends MongoRepository<Contact, String> {
    Contact findByNumbers(String number);

    Contact findByName(String name);

    List<Contact> findByUsername(String username);

    Contact findById(String contactId, User sender);
}
