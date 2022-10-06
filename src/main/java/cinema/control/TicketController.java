package cinema.control;

import cinema.model.Session;
import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.TicketService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/addTicket")
    public String addTicket(HttpSession session) {
        Optional<Ticket> purchasedTicket = ticketService.addTicket(new Ticket(
                ((Session) session.getAttribute("cinemaSession")).getId(),
                Integer.parseInt((String) session.getAttribute("cellHall")),
                Integer.parseInt((String) session.getAttribute("rowHall")),
                ((User) session.getAttribute("user")).getId()
        ));
        if (purchasedTicket.isEmpty()) {
            return "redirect:/cinemaSessions?fail=true";
        }
        return "redirect:/formPurchasedTicket";
    }

    @GetMapping("/formPurchasedTicket")
    public String formPurchasedTicket(Model model, HttpSession session) {
        model.addAttribute("cinemaSession", session.getAttribute("cinemaSession"));
        model.addAttribute("cellHall", session.getAttribute("cellHall"));
        model.addAttribute("rowHall", session.getAttribute("rowHall"));
        model.addAttribute("user", session.getAttribute("user"));
        return "formPurchasedTicket";
    }
}
