package contactManager.services;

import contactManager.data.repository.Contacts;
import contactManager.data.repository.Messages;
import contactManager.data.repository.Users;
import contactManager.dtos.request.*;
import contactManager.dtos.response.GetContactResponse;
import contactManager.dtos.response.GetMessageResponse;
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
    private Messages messages;
    @Autowired
    private Contacts contacts;
    @Autowired
    private UserServices userServices;
    @BeforeEach
    public void setUp(){
        users.deleteAll();
        contacts.deleteAll();
        messages.deleteAll();
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
        createContactRequest.setName("username");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
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
        createContactRequest.setName("username");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);
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
        createContactRequest.setName("username");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        GetContactRequest getContactRequest = new GetContactRequest();
        getContactRequest.setNumber(createContactRequest.getNumber());

        GetContactResponse getContactResponse = new GetContactResponse();
        getContactResponse.setName(createContactRequest.getName());
        getContactResponse.setNumber(createContactRequest.getNumber());

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
        createContactRequest.setName("username");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setName(createContactRequest.getName());
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
        createContactRequest.setName("username");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setName(createContactRequest.getName());
        editContactRequest.setNewUsername("newUsername");
        editContactRequest.setNewNumber("1234");
        editContactRequest.setNewEmailAddress("banks@gmail.com");
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
        createContactRequest.setName("username");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        CreateContactRequest createContactRequest1 = new CreateContactRequest();
        createContactRequest1.setName("username1");
        createContactRequest1.setNumber("4321");
        createContactRequest1.setEmail("ike@gmail.com");
        userServices.addContact(createContactRequest1);
        FindContactRequest findContactRequest = new FindContactRequest();
        findContactRequest.setUsername(registerRequest.getUsername());
        userServices.findAllContact(findContactRequest);
        assertEquals(2, userServices.numberOfContacts());
    }
    @Test
    public void testToSendMessage(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setName("name");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname");
        registerRequest1.setLastname("lastname");
        registerRequest1.setUsername("michael");
        registerRequest1.setPassword("password");
        userServices.registerUser(registerRequest1);

        CreateContactRequest createContactRequest1 = new CreateContactRequest();
        createContactRequest1.setName("michael");
        createContactRequest1.setNumber("4321");
        createContactRequest1.setEmail("ike@gmail.com");
        userServices.addContact(createContactRequest1);

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSender(registerRequest.getUsername());
        messageRequest.setReceiver(registerRequest1.getUsername());
        messageRequest.setMessage("messages");
        userServices.sendMessage(messageRequest);
        assertEquals(1, userServices.numberOfMessages());
    }
    @Test
    public void testToReceiveMessage(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setName("username");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname");
        registerRequest1.setLastname("lastname");
        registerRequest1.setUsername("user");
        registerRequest1.setPassword("password");

        userServices.registerUser(registerRequest1);

        CreateContactRequest createContactRequest1 = new CreateContactRequest();
        createContactRequest1.setName("username");
        createContactRequest1.setNumber("1234");
        createContactRequest1.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest1);

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSender(registerRequest.getUsername());
        messageRequest.setReceiver(registerRequest1.getUsername());
        messageRequest.setMessage("messages");
        var message = userServices.sendMessage(messageRequest);

        GetMessageSingleRequest getMessageRequest = new GetMessageSingleRequest();
        getMessageRequest.setSender(messageRequest.getSender());
        getMessageRequest.setMessageId(message.getMessageId());
        userServices.getMessage(getMessageRequest);

        GetMessageResponse getMessageResponse = new GetMessageResponse();
        getMessageResponse.setMessage(messageRequest.getMessage());
        getMessageResponse.setMessageId(message.getMessageId());
        getMessageResponse.setDate(message.getDate());
        assertEquals(getMessageResponse, userServices.getMessage(getMessageRequest));
    }
    @Test
    public void testToDeleteMessage(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setName("username");
        createContactRequest.setNumber("12324");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname");
        registerRequest1.setLastname("lastname");
        registerRequest1.setUsername("user");
        registerRequest1.setPassword("password");

        userServices.registerUser(registerRequest1);

        CreateContactRequest createContactRequest1 = new CreateContactRequest();
        createContactRequest1.setName("mike");
        createContactRequest1.setNumber("1234");
        createContactRequest1.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest1);

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSender(registerRequest.getUsername());
        messageRequest.setReceiver(registerRequest1.getUsername());
        messageRequest.setMessage("messages");
        var message = userServices.sendMessage(messageRequest);

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setMessageId(message.getMessageId());
        userServices.deleteMessage(deleteMessageRequest);
        assertEquals(0, userServices.numberOfMessages());
    }
    @Test
    public void testToGetContactByCharacter(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        userServices.registerUser(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setName("mic");
        createContactRequest.setNumber("1234");
        createContactRequest.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest);

        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname");
        registerRequest1.setLastname("lastname");
        registerRequest1.setUsername("user");
        registerRequest1.setPassword("password");
        userServices.registerUser(registerRequest1);

        CreateContactRequest createContactRequest1 = new CreateContactRequest();
        createContactRequest1.setName("mike");
        createContactRequest1.setNumber("12334");
        createContactRequest1.setEmail("michael@gmail.com");
        userServices.addContact(createContactRequest1);
        FindContactRequest findContactRequest = new FindContactRequest();
        findContactRequest.setUsername("u");
        userServices.findContactsByAlphabet(findContactRequest);
        assertEquals(2, userServices.numberOfContacts());
    }


}