package contactManager.utils;

import contactManager.data.model.Contact;
import contactManager.data.model.Message;
import contactManager.data.model.User;
import contactManager.dtos.request.*;
import contactManager.dtos.response.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

public class Mappers {
    public static User map(RegisterRequest registerRequest) {
        //validateInput(registerRequest.getFirstname());
        //validateInput(registerRequest.getLastname());
        validate1(registerRequest.getFirstname());
        validate2(registerRequest.getLastname());
        validate3(registerRequest.getUsername());
        validate4(registerRequest.getPassword());
        User user = new User();
        user.setFirstname((registerRequest.getFirstname()));
        user.setLastname(registerRequest.getLastname());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        return user;
    }
    private static void validate1 (String registerRequest){
        if(registerRequest == null || registerRequest.isEmpty()) throw new NullPointerException("Firstname field is required");
    }
    private static void validate2(String registerRequest){
        if(registerRequest == null || registerRequest.isEmpty()) throw new NullPointerException("Lastname field is required");
    }
    private static void validate3(String registerRequest){
        if(registerRequest == null || registerRequest.isEmpty()) throw new NullPointerException("Username field is required");
    }
    private static void validate4(String registerRequest){
        if(registerRequest == null || registerRequest.isEmpty()) throw new NullPointerException("Password field is required");
    }
    private static void validateInput(String registerRequest) {
        if (!registerRequest.matches("^\\d+(\\.\\d+)?$")) throw new InputMismatchException("Invalid Input");
    }

    public static RegisterUserResponse map(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setId(user.getId());
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));
        return registerUserResponse;
    }
    public static Contact map1(CreateContactRequest createContactRequest){
        validateCont(createContactRequest.getName());
        validateCont1(createContactRequest.getNumber());
        validateEmail(createContactRequest.getEmail());
        Contact contact = new Contact();
        contact.setName(createContactRequest.getName());
        contact.setEmail(createContactRequest.getEmail());
        contact.setNumbers(createContactRequest.getNumber());
        return contact;
    }
    public static Message map(ReceiveMessageRequest receiveMessageRequest){
        Message message = new Message();
        message.setSender(receiveMessageRequest.getSender());
        return message;
    }
    public static MessageResponse mapMessageResponse(Message message){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageId(message.getMessageId());
        messageResponse.setMessage(message.getContent());
        messageResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));
        return messageResponse;
    }
    public static Message map(MessageRequest messageRequest){
        Message message = new contactManager.data.model.Message();
        message.setContent(messageRequest.getMessage());
        message.setSender(messageRequest.getSender());
        message.setReceiver(messageRequest.getReceiver());
        return message;
    }
    private static void validateEmail(String email){
        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) throw new InputMismatchException("Invalid Email");
    }
    private static void validateCont (String registerRequest){
        if(registerRequest == null) throw new NullPointerException("Username field is required");
    }
    private static void validateCont1(String registerRequest){
        if(registerRequest == null) throw new NullPointerException("PhoneNumber field is required");
    }
    public static CreateContactResponse map1(Contact contact){
        CreateContactResponse createContactResponse = new CreateContactResponse();
        createContactResponse.setId(contact.getId());
        createContactResponse.setUsername(contact.getName());
        createContactResponse.setNumber(contact.getNumbers());
        createContactResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));
        return createContactResponse;
    }
    public static Contact map3(EditContactRequest editContactRequest){
        validateInput(editContactRequest.getNewNumber());
        validateCont(editContactRequest.getNewUsername());
        validateCont1(editContactRequest.getNewNumber());
        validateEmail(editContactRequest.getNewEmailAddress());
        Contact contact = new Contact();
        contact.setNumbers(editContactRequest.getNewNumber());
        contact.setName(editContactRequest.getName());
        contact.setEmail(editContactRequest.getNewEmailAddress());
        return contact;
    }
    public static EditContactResponse map3(Contact contact){
        EditContactResponse editContactResponse = new EditContactResponse();
        editContactResponse.setId(contact.getId());
        editContactResponse.setName(contact.getName());
        editContactResponse.setNumber(contact.getNumbers());
        editContactResponse.setEmail(contact.getEmail());
        editContactResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));
        return editContactResponse;
    }
    public static GetMessageResponse map(Message message){
        GetMessageResponse getMessageResponse = new GetMessageResponse();
        getMessageResponse.setMessageId(message.getMessageId());
        getMessageResponse.setMessage(message.getContent());
        getMessageResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));
        return getMessageResponse;
    }
    public static DeleteContactResponse mapDeleteResponseWith(Contact contact) {
        DeleteContactResponse deleteContactResponse =new DeleteContactResponse();
        deleteContactResponse.setUsername(contact.getName());
        return deleteContactResponse;
    }
    public static DeleteMessageResponse mapDeleteMessage(Message message) {
        DeleteMessageResponse deleteMessageResponse =new DeleteMessageResponse();
        deleteMessageResponse.setSender(message.getSender());
        return deleteMessageResponse;
    }
    public static GetContactResponse map4(Contact contact){
        GetContactResponse getContactResponse = new GetContactResponse();
        getContactResponse.setName(contact.getName());
        getContactResponse.setNumber(contact.getNumbers());
        return getContactResponse;
    }
}
