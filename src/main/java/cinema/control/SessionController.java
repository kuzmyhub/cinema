package cinema.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cinema.service.SessionService;

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


}
