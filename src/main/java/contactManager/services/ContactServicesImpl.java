package contactManager.services;

import contactManager.data.model.Contact;
import contactManager.data.repository.Contacts;
import contactManager.dtos.request.CreateContactRequest;
import contactManager.dtos.request.DeleteContactRequest;
import contactManager.dtos.request.EditContactRequest;
import contactManager.dtos.request.FindContactRequest;
import contactManager.dtos.response.DeleteContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static contactManager.utils.Mappers.*;

@Service
public class ContactServicesImpl implements ContactServices {
    @Autowired
    private Contacts contacts;
    @Override
    public Contact createContact(CreateContactRequest createContactRequest) {
        Contact contact = map1(createContactRequest);
        return contacts.save(contact);
    }

    @Override
    public Contact editContact(EditContactRequest editContactRequest) {
        Contact newContact = map3(editContactRequest);
        return contacts.save(newContact);
    }

    @Override
    public Contact findContact(FindContactRequest findContactRequest, Contact findContact) {
        return contacts.findContactBy(findContact.getUsername());
    }

    @Override
    public DeleteContactResponse deleteContact(DeleteContactRequest deleteContactRequest, Contact contact) {
        contacts.delete(contact);
        return mapDeleteResponseWith(contact);
    }
    @Override
    public long numberOfContacts() {
        return contacts.findAll().size();
    }
}
