package contactManager.services;

import contactManager.data.model.Contact;
import contactManager.dtos.request.CreateContactRequest;
import contactManager.dtos.request.DeleteContactRequest;
import contactManager.dtos.request.EditContactRequest;
import contactManager.dtos.request.FindContactRequest;
import contactManager.dtos.response.DeleteContactResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactServices  {
    Contact createContact(CreateContactRequest createContactRequest);
    Contact editContact(EditContactRequest editContactRequest);
    Contact findContact(FindContactRequest findContactRequest, Contact findContact);
    DeleteContactResponse deleteContact(DeleteContactRequest deleteContactRequest, Contact contact);
    long numberOfContacts();
}
