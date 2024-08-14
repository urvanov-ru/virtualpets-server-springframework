package ru.urvanov.virtualpets.server.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.constraints.Min;
import ru.urvanov.virtualpets.server.service.UserService;
import ru.urvanov.virtualpets.server.service.domain.UserAccessRights;
import ru.urvanov.virtualpets.server.service.exception.UserNotFoundException;

@Controller
@RequestMapping("site/admin")
public class AdminController extends ControllerBase {

    @Autowired
    private UserService userService;

    @GetMapping
    public String adminHome() {
        return "admin/panel";
    }

    @GetMapping("user/{userId}")
    public String showUserAccess(Model model,
            @PathVariable("userId") @Min(1) Integer userId)
            throws UserNotFoundException {
        UserAccessRights userAccessRights = userService
                .findUserAccessRights(userId);
        model.addAttribute("userAccessRights", userAccessRights);
        return "admin/useraccessrights";
    }

    @PostMapping("user/{userId}")
    public String saveUserAccess(Model model,
            @PathVariable("userId") @Min(1) Integer userId,
            @ModelAttribute UserAccessRights userAccessRights)
            throws UserNotFoundException {
        userAccessRights.setId(userId);
        UserAccessRights result = userService
                .saveUserAccessRights(userAccessRights);
        model.addAttribute("userAccessRights", result);
        model.addAttribute("saved", true);
        return "admin/useraccessrights";
    }
}
