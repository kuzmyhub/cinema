package cinema.control;

import cinema.model.User;
import cinema.service.UserService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formFillingData")
    public String formFilingData() {
        return "fillingData";
    }

    @PostMapping("/filling")
    public String filling(@ModelAttribute User user, HttpSession session) {
        User addedUser = userService.addUser(user);
        return "fillingData";
    }
}
