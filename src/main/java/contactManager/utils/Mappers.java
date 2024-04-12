package contactManager.utils;

import contactManager.data.model.Contact;
import contactManager.data.model.User;
import contactManager.dtos.request.CreateContactRequest;
import contactManager.dtos.request.EditContactRequest;
import contactManager.dtos.request.RegisterRequest;
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
        if(registerRequest == null) throw new NullPointerException("Firstname field is required");
    }
    private static void validate2(String registerRequest){
        if(registerRequest == null) throw new NullPointerException("Lastname field is required");
    }
    private static void validate3(String registerRequest){
        if(registerRequest == null) throw new NullPointerException("Username field is required");
    }
    private static void validate4(String registerRequest){
        if(registerRequest == null) throw new NullPointerException("Password field is required");
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
        validateCont(createContactRequest.getUsername());
        validateCont1(createContactRequest.getNumber());
        validateEmail(createContactRequest.getEmail());
        Contact contact = new Contact();
        contact.setUsername(createContactRequest.getUsername());
        contact.setEmail(createContactRequest.getEmail());
        contact.setNumbers(createContactRequest.getNumber());
        return contact;
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
        createContactResponse.setUsername(contact.getUsername());
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
        contact.setUsername(editContactRequest.getNewUsername());
        contact.setNumbers(editContactRequest.getNewNumber());
        contact.setEmail(editContactRequest.getNewEmailAddress());
        return contact;
    }
    public static EditContactResponse map3(Contact contact){
        EditContactResponse editContactResponse = new EditContactResponse();
        editContactResponse.setId(contact.getId());
        editContactResponse.setUsername(contact.getUsername());
        editContactResponse.setNumber(contact.getNumbers());
        editContactResponse.setEmail(contact.getEmail());
        editContactResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));
        return editContactResponse;
    }
    public static DeleteContactResponse mapDeleteResponseWith(Contact contact) {
        DeleteContactResponse deleteContactResponse =new DeleteContactResponse();
        deleteContactResponse.setUsername(contact.getUsername());
        return deleteContactResponse;
    }
    public static GetContactResponse map4(Contact contact){
        GetContactResponse getContactResponse = new GetContactResponse();
        getContactResponse.setUsername(contact.getUsername());
        return getContactResponse;
    }
}
