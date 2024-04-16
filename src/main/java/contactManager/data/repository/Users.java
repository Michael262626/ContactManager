package contactManager.data.repository;

import contactManager.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends MongoRepository<User, String> {
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
