package contactManager.services;

import contactManager.data.model.Contact;
import contactManager.dtos.request.*;
import contactManager.dtos.response.*;

import java.util.List;

public interface UserServices {
    RegisterUserResponse registerUser(RegisterRequest registerRequest);
    void login(LoginRequest loginRequest);
    void DeleteAccount(DeleteAccountRequest deleteAccountRequest);
    CreateContactResponse addContact(CreateContactRequest createContactRequest);
    EditContactResponse editContact(EditContactRequest editContactRequest);
    void deleteContact(DeleteContactRequest deleteContactRequest);
    void logout(LogoutRequest logoutRequest);
     List<Contact>  findAllContact();
    GetContactResponse findContact(GetContactRequest getContactRequest);
    long numberOfUsers();
    long numberOfContacts();
}
