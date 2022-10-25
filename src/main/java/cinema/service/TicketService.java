package cinema.service;

import cinema.model.Ticket;
import cinema.store.TicketDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@ThreadSafe
@Repository
public class TicketService {
    private final TicketDBStore store;

    public TicketService(TicketDBStore store) {
        this.store = store;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return store.addTicket(ticket);
    }
}
