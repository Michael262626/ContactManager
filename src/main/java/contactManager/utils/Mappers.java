package contactManager.utils;

import contactManager.data.model.Contact;
import contactManager.data.model.User;
import contactManager.dtos.request.CreateContactRequest;
import contactManager.dtos.request.EditContactRequest;
import contactManager.dtos.request.RegisterRequest;
import contactManager.dtos.response.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mappers {
    public static User map(RegisterRequest registerRequest) {
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
        Contact contact = new Contact();
        contact.setUsername(createContactRequest.getUsername());
        contact.setNumbers(createContactRequest.getNumber());
        return contact;
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
        validateCont(editContactRequest.getNewUsername());
        validateCont1(editContactRequest.getNewNumber());
        Contact contact = new Contact();
        contact.setUsername(editContactRequest.getNewUsername());
        contact.setNumbers(editContactRequest.getNewNumber());
        return contact;
    }
    public static EditContactResponse map3(Contact contact){
        EditContactResponse editContactResponse = new EditContactResponse();
        editContactResponse.setId(contact.getId());
        editContactResponse.setUsername(contact.getNumbers());
        editContactResponse.setNumber(contact.getNumbers());
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
