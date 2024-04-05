package contactManager.controller;

import contactManager.data.model.Contact;
import contactManager.dtos.request.*;
import contactManager.dtos.response.ApiResponse;
import contactManager.dtos.response.GetContactResponse;
import contactManager.exceptions.*;
import contactManager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/trueCaller")
public class userController {
    @Autowired
    private UserServices userServices;
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try{
            var result = userServices.registerUser(registerRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @PostMapping("/{createContact}")
    public ResponseEntity<?> createContact(@RequestBody CreateContactRequest createContactRequest){
        try{
            var result = userServices.addContact(createContactRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }

    @GetMapping("/{contacts}")
    public ResponseEntity<ApiResponse> getContact(@RequestBody GetContactRequest getContactRequest) {
        try {
            var contact = userServices.findContact(getContactRequest);
            return ResponseEntity.ok(new ApiResponse(true, contact));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/getAll/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            List<Contact> contacts = userServices.findAllContact();
                return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PutMapping("/{edit}")
    public ResponseEntity<?> editContact(@RequestBody EditContactRequest editContactRequest) {
        try{
            var result = userServices.editContact(editContactRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/contact")
    public ResponseEntity<?> deleteContact(@RequestBody DeleteContactRequest deleteContactRequest) {
        try{
            userServices.deleteContact(deleteContactRequest);
            return new ResponseEntity<>(new ApiResponse(true,NO_CONTENT), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/user")
    public  ResponseEntity<?> deleteUser(@RequestBody DeleteAccountRequest deleteAccountRequest){
        try{
            userServices.DeleteAccount(deleteAccountRequest);
            return new ResponseEntity<>(new ApiResponse(true,NO_CONTENT), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @PatchMapping("/login/user")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        try{
                userServices.login(loginRequest);
                return new ResponseEntity<>(new ApiResponse(true, NO_CONTENT), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @PatchMapping("/logout/user")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutRequest logoutRequest) {
        try {
            userServices.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, NO_CONTENT), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
}
