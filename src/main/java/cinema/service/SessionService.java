package cinema.service;

import cinema.model.Session;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import cinema.store.SessionDBStore;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SessionService {

    private final SessionDBStore store;

    public SessionService(SessionDBStore store) {
        this.store = store;
    }

    public List<Session> findAll() {
        return store.findAll();
    }

    public Optional<Session> findById(int id) {
        return store.findById(id);
    }
}
