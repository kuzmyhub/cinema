package cinema.control;

import cinema.model.Session;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cinema.service.SessionService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (chosenSession.isPresent()) {
            session.setAttribute("cinemaSession",
                    sessionService.findById(cinemaSession.getId()));
        }
        System.out.println(session.getAttribute("cinemaSession"));
        System.out.println(cinemaSession);
        return "rowHall";
    }

    @PostMapping("/choseRowHall")
    public String rowHall() {
        List<String> rowsHall = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            rowsHall.add(String.valueOf(i));
        }
        return "";
    }
}
