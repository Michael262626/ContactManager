package contactManager.services;

import contactManager.data.model.Contact;
import contactManager.data.model.User;
import contactManager.data.repository.Contacts;
import contactManager.data.repository.Users;
import contactManager.dtos.request.*;
import contactManager.dtos.response.*;
import contactManager.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static contactManager.utils.Mappers.*;

@Service
public class UserServicesImpl implements UserServices{
    @Autowired
    private Users users;
    @Autowired
    private Contacts contacts;
    @Override
    public RegisterUserResponse registerUser(RegisterRequest registerRequest) {
        registerRequest.setUsername(registerRequest.getUsername().toLowerCase());
        validate(registerRequest.getUsername());
        User user = map(registerRequest);
        users.save(user);
        RegisterUserResponse result = map(user);
        return result;
    }
    private void validate(String username) {
        boolean userExist = users.existsByUsername(username);
        if (userExist) throw new UserNameExistException("User already exist");
    }
    @Override
    public void login(LoginRequest loginRequest) {
        User user = findById(loginRequest.getUsername());
        if (isPasswordIncorrect(user, loginRequest.getPassword())) throw new InvalidPasswordException("Invalid password");
        user.setLoggedIn(false);
        users.save(user);
    }
    private boolean isPasswordIncorrect(User foundUser, String password) {
        if (foundUser == null) {
            throw new NullPointerException("Diary object is null");
        }
        if (!foundUser.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid Password");
        }
        return false;
    }
    private User findById(String username) {
        User foundUser = users.findByUsername(username);
        if (foundUser == null) throw new UsernameNotFoundException(String.format("%s not found", username));
        return foundUser;
    }

    @Override
    public void DeleteAccount(DeleteAccountRequest deleteAccountRequest) {
        User user = findById(deleteAccountRequest.getUsername());
        users.delete(user);
    }

    @Override
    public CreateContactResponse addContact(CreateContactRequest createContactRequest) {
        if(isContactExisting(createContactRequest.getUsername())) throw new ContactExistException("This contact already exist");
        Contact contact = map1(createContactRequest);
        contacts.save(contact);
        CreateContactResponse response = map1(contact);
        return response;
    }
    private boolean isContactExisting(String foundContact){
        for (Contact contact : contacts.findAll()) {
            if (contact.getUsername().equals(foundContact)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EditContactResponse editContact(EditContactRequest editContactRequest) {
        Contact contact = findContactBy(editContactRequest.getNewUsername());
        Contact set = map3(editContactRequest);
        contacts.save(set);
        EditContactResponse updateResponse = map3(contact);
        contacts.delete(contact);
        return updateResponse;
    }
    private Contact findContactBy(String username) {
        Contact foundContact = contacts.findByUsername(username);
        if (foundContact == null) throw new ContactNotFoundException(String.format("%s not found", username));
        return foundContact;
    }

    @Override
    public void deleteContact(DeleteContactRequest deleteContactRequest) {
        List<Contact> matchingContacts = contacts.findContactsBy(deleteContactRequest.getUsername());
        if(matchingContacts.isEmpty()) {
            throw new ContactNotFoundException("Contact not found for : " + deleteContactRequest.getUsername());
        } else {
            Contact contactToDelete = matchingContacts.getFirst();
            contacts.delete(contactToDelete);
        }
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        User user = findById(logoutRequest.getUsername());
        user.setLoggedIn(true);
        users.save(user);
    }

    @Override
    public List<Contact>  findAllContact() {
        List<Contact> contact = contacts.findAll();
        if (contact.isEmpty()) {
            throw new NotFoundException("No contacts found");
        }
        return contact;
    }

    @Override
    public GetContactResponse findContact(GetContactRequest getContactRequest) {
        Contact contact = contacts.findContactBy(getContactRequest.getUsername());
        GetContactResponse result = map4(contact);
        if (contact.getUsername() == null || contact.getUsername().isEmpty()) {
            throw new UsernameNotFoundException("Contact was not found for : " + getContactRequest.getUsername());
        }
        return result;
    }
    @Override
    public long numberOfUsers() {
        return users.findAll().size();
    }

    @Override
    public long numberOfContacts() {
        return contacts.findAll().size();
    }
}
