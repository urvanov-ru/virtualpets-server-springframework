package ru.urvanov.virtualpets.server.controller.site;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import ru.urvanov.virtualpets.server.service.UserService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;

@Controller
@RequestMapping("site")
public class ProfileController {
    private static final Logger logger = LoggerFactory
            .getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserPetDetails userPetDetails;
    
    /**
     * Получает данные от социальной сети и сохраняет в пользователях игры.
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String home(Locale locale, Model model, HttpServletRequest request) {
        logger.info("Welcome home! The client locale is {}.", locale);
        UserProfile userProfile = userService.getProfile(userPetDetails);
        model.addAttribute("userProfile", userProfile);
        return "profile";
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
