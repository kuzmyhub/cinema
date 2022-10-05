package cinema.store;

import cinema.model.User;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
@ThreadSafe
public class UserDBStore {

    private static final String ADD = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)";

    private static final Logger LOG
            = LoggerFactory.getLogger(SessionDBStore.class.getName());

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> addUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    user.setId(it.getInt("id"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in log example", e);
        }
        return Optional.empty();
    }
}
