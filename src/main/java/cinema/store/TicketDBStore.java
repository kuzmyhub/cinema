package cinema.store;

import cinema.model.Ticket;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@ThreadSafe
@Repository
public class TicketDBStore {

    private static final String ADD_TICKET = "INSERT INTO ticket(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";

    private static final Logger LOG
            = LoggerFactory.getLogger(SessionDBStore.class.getName());

    private final BasicDataSource pool;

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_TICKET,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getPosRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    ticket.setId(it.getInt("id"));
                    return Optional.of(ticket);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception in log example", e);
        }
        return Optional.empty();
    }
}
