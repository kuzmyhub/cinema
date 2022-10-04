package cinema.store;

import cinema.model.Session;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
public class SessionDBStore {

    private final static String ADD_SESSIONS = "insert into sessions(name) values('DetourMortel'), ('Matrix'), ('PulpFiction')";

    private final static String FIND_ALL = "SELECT * FROM sessions";

    private final static String FIND_BY_ID = "SELECT name FROM sessions WHERE id = (?)";

    private static final Logger LOG
            = LoggerFactory.getLogger(SessionDBStore.class.getName());

    private final BasicDataSource pool;

    public SessionDBStore(BasicDataSource pool) {
        this.pool = pool;

        addSessions();
    }

    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(
                            new Session(
                                    it.getInt("id"),
                                    it.getString("name")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return sessions;
    }

    public Optional<Session> findById(int id) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new Session(
                            id,
                            it.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return Optional.empty();
    }

    private void addSessions() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     ADD_SESSIONS
             )) {
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
    }
}
