package contactManager.services;

import contactManager.data.model.Contact;
import contactManager.data.model.Message;
import contactManager.data.model.User;
import contactManager.data.repository.Contacts;
import contactManager.data.repository.Messages;
import contactManager.data.repository.Users;
import contactManager.dtos.request.*;
import contactManager.dtos.response.*;
import contactManager.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static contactManager.utils.Mappers.*;

@Service
public class UserServicesImpl implements UserServices{
    @Autowired
    private Users users;
    @Autowired
    private Contacts contacts;
    @Autowired
    private Messages messages;
    @Override
    public RegisterUserResponse registerUser(RegisterRequest registerRequest) {
        registerRequest.setUsername(registerRequest.getUsername().toLowerCase());
        validate(registerRequest.getUsername());
        User user = map(registerRequest);
        users.save(user);
        return map(user);
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
        return map1(contact);
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

    @Override
    public MessageResponse sendMessage(MessageRequest messageRequest) {
        findById(messageRequest.getSender());
        findById(messageRequest.getReceiver());
        Message set = map(messageRequest);
        messages.save(set);
        return mapMessageResponse(set);
    }

    @Override
    public GetMessageResponse getMessage(GetMessageSingleRequest getMessageRequest) {
        Message message = messages.findMessageByMessageId(getMessageRequest.getMessageId());
        if (message == null || message.getSender() == null ) {
            throw new UsernameNotFoundException("Message not found ");
        }
        return map(message);
    }
    private boolean loginStatus(User user) {
        if(!user.isLoggedIn()) throw new UsernameNotFoundException("User is not logged in");
        return true;
    }

    @Override
    public List<Message> getConversation(GetMessageRequest getMessageRequest) {
        User user = findById(getMessageRequest.getSender());
        List<Message> messageList = messages.findAll();
        if (messageList.isEmpty()) {
            throw new NotFoundException("No messages found");
        }
        return messageList;
    }

    @Override
    public void deleteMessage(DeleteMessageRequest deleteMessageRequest) {
        List<Message> matchingMessage = messages.findAllBy(deleteMessageRequest.getMessageId());
        if(matchingMessage.isEmpty()) {
            throw new ContactNotFoundException("Message not found");
        } else {
            Message messageToDelete = matchingMessage.getFirst();
            messages.delete(messageToDelete);
        }
    }

    @Override
    public long numberOfMessages() {
        return messages.findAll().size();
    }
}
