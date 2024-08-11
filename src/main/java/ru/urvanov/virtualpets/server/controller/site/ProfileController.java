package ru.urvanov.virtualpets.server.controller.site;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.service.UserService;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;
import ru.urvanov.virtualpets.server.service.exception.UserNotFoundException;

@Controller
@RequestMapping("site/user/profile")
public class ProfileController extends ControllerBase {
    private static final Logger logger = LoggerFactory
            .getLogger(ProfileController.class);

    @Autowired
    private UserService userService;
    
    /**
     * Получает данные от социальной сети и сохраняет
     * в пользователях игры.
     * @throws UserNotFoundException 
     */
    @GetMapping
    public String home(
            Locale locale,
            Model model,
            HttpServletRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws UserNotFoundException {
        logger.info("Welcome home! The client locale is {}.", locale);
        UserProfile userProfile = userService.getProfile(
                userDetailsImpl.getUserId());
        model.addAttribute("userProfile", userProfile);
        return "user/profile";
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
