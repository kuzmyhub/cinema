package cinema.controller;

import cinema.model.User;
import cinema.service.UserService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formFillingData")
    public String formFilingData(Model model,
                                 @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "fillingData";
    }

    @PostMapping("/filling")
    public String filling(@ModelAttribute User user, HttpSession session) {
        Optional<User> addedUser = userService.addUser(user);
        if (addedUser.isEmpty()) {
            return "redirect:/formFillingData?fail=true";
        }
        session.setAttribute("user", addedUser.get());
        return "redirect:/addTicket";
    }
}
