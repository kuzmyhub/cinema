package cinema.store;

import cinema.model.Ticket;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@ThreadSafe
@Repository
public class TicketDBStore {

    private static final Logger LOG
            = LoggerFactory.getLogger(SessionDBStore.class.getName());

    private final BasicDataSource pool;

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public void add(Ticket ticket) {

    }
}
