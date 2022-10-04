package service;

import model.Session;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import store.SessionDBStore;

import java.util.List;

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
}
