package contactManager.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("User")
public class User {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private boolean isLoggedIn = true;
    @DBRef
    private List<Contact> contacts = new ArrayList<>();
}
