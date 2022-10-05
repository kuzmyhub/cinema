package cinema.control;

import cinema.model.Session;
import jdk.swing.interop.SwingInterOpUtils;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import cinema.service.SessionService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@ThreadSafe
@Controller
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/cinemaSessions")
    public String sessions(Model model) {
        model.addAttribute("cinemaSessions", sessionService.findAll());
        return "cinemaSessions";
    }

    @GetMapping("/choseSession")
    public String choseSession(@ModelAttribute Session cinemaSession,
                               HttpServletRequest req) {
        HttpSession session = req.getSession();
        Optional<Session> chosenSession
                = sessionService.findById(cinemaSession.getId());
        chosenSession.ifPresent(value -> session.setAttribute("cinemaSession",
                value));
        return "rowHall";
    }

    @PostMapping("/choseRowHall")
    public String rowHall(@ModelAttribute("rowHall") String rowHall,
                          HttpSession session) {
        session.setAttribute("rowHall", rowHall);
        return "cellHall";
    }

    @PostMapping("/choseCellHall")
    public String cellHall(@ModelAttribute("cellHall") String cellHall,
                           HttpSession session) {
        session.setAttribute("cellHall", cellHall);
        return "redirect:/sessionInformation";
    }

    @GetMapping("/sessionInformation")
    public String sessionInformation(Model model, HttpSession session) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("cinemaSession", ((Session) session.getAttribute("cinemaSession")).getName());
        attributes.put("rowHall", (String) session.getAttribute("rowHall"));
        attributes.put("cellHall", (String) session.getAttribute("cellHall"));
        model.addAllAttributes(attributes);
        return "sessionInformation";
    }
}
