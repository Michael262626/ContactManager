package contactManager.services;

import contactManager.data.repository.Contacts;
import contactManager.data.repository.Users;
import contactManager.dtos.request.*;
import contactManager.dtos.response.GetContactResponse;
import contactManager.exceptions.ContactExistException;
import contactManager.exceptions.InvalidPasswordException;
import contactManager.exceptions.UserNameExistException;
import contactManager.exceptions.UsernameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServicesImplTest {
    @Autowired
    private Users users;
    @Autowired
    private Contacts contacts;
    @Autowired
    private UserServices userServices;
    @BeforeEach
    public void setUp(){
        users.deleteAll();
        contacts.deleteAll();
    }
    @Test
    public void testToRegisterUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);
        assertEquals(1, userServices.numberOfUsers());
    }
    @Test
    public void testToRegisterSameUserAndThrowAnException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);
        try{
        userServices.registerUser(registerRequest);
    }catch (UserNameExistException e){
            assertEquals(e.getMessage(), "User already exist");
        }
    }
    @Test
    public void testToLogin(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword(registerRequest.getPassword());
        userServices.login(loginRequest);
        assertFalse(users.findByUsername(loginRequest.getUsername().toLowerCase()).isLoggedIn());
    }
    @Test
    public void testToLoginNonExistingUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("name");
        loginRequest.setPassword("password");
        try{
        userServices.login(loginRequest);
    }catch (UsernameNotFoundException ex){
            assertEquals(ex.getMessage(), "name not found");
        }
    }
    @Test
    public void testToLoginWithIncorrectPassword(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword("password");
        try{
            userServices.login(loginRequest);
        }catch (InvalidPasswordException ex){
            assertEquals(ex.getMessage(), "Invalid password");
        }
    }
    @Test
    public void testToLogout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword(registerRequest.getPassword());
        userServices.login(loginRequest);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername(loginRequest.getUsername());
        userServices.logout(logoutRequest);
        assertTrue(users.findByUsername(loginRequest.getUsername().toLowerCase()).isLoggedIn());
    }
    @Test
    public void testToLogoutWithIncorrectNameThrowsAnException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword(registerRequest.getPassword());
        userServices.login(loginRequest);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("name");
        try{
        userServices.logout(logoutRequest);
    }catch (UsernameNotFoundException ex){
            assertEquals(ex.getMessage(), "name not found");
        }
    }
    @Test
    public void testToCreateContacts(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("username");
        createContactRequest.setNumber("1234");
        userServices.addContact(createContactRequest);
        assertEquals(1, userServices.numberOfContacts());
    }
    @Test
    public void testToCreateExistingContactAndThrowsAnException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("username");
        createContactRequest.setNumber("1234");
        try{
            userServices.addContact(createContactRequest);
            userServices.addContact(createContactRequest);
    }catch (ContactExistException ex){
            assertEquals(ex.getMessage(), "This contact already exist");
        }
    }
    @Test
    public void testToFindSavedContact(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("username");
        createContactRequest.setNumber("1234");
        userServices.addContact(createContactRequest);

        GetContactRequest getContactRequest = new GetContactRequest();
        getContactRequest.setUsername(createContactRequest.getUsername());

        GetContactResponse getContactResponse = new GetContactResponse();
        getContactResponse.setUsername(getContactRequest.getUsername());

        userServices.findContact(getContactRequest);
        assertEquals(getContactResponse,userServices.findContact(getContactRequest));
    }
    @Test
    public void testToDeleteSavedContact(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("username");
        createContactRequest.setNumber("1234");
        userServices.addContact(createContactRequest);

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setUsername(createContactRequest.getUsername());
        deleteContactRequest.setNumbers(createContactRequest.getNumber());
        userServices.deleteContact(deleteContactRequest);
        assertEquals(0, userServices.numberOfContacts());
    }
    @Test
    public void testToEditContact(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("username");
        createContactRequest.setNumber("1234");
        userServices.addContact(createContactRequest);

        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setNewUsername(createContactRequest.getUsername());
        editContactRequest.setNewNumber("4321");
        userServices.editContact(editContactRequest);
        assertEquals(1, userServices.numberOfContacts());
    }
    @Test
    public void testFindAllSavedContact(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("username");
        createContactRequest.setNumber("1234");
        userServices.addContact(createContactRequest);

        CreateContactRequest createContactRequest1 = new CreateContactRequest();
        createContactRequest1.setUsername("username1");
        createContactRequest1.setNumber("4321");
        userServices.addContact(createContactRequest1);
        userServices.findAllContact();
        assertEquals(2, userServices.numberOfContacts());
    }


}