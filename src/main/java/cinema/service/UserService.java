package cinema.service;

import cinema.model.User;
import cinema.store.SessionDBStore;
import cinema.store.UserDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

@ThreadSafe
@Service
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public User addUser(User user) {
        return store.addUser(user);
    }
}
