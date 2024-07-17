package ru.urvanov.virtualpets.server.controller.site;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("site")
public class HomeController extends ControllerBase {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Locale locale) {
        logger.info("Welcome home! The client locale is {}.", locale);
        return "home";
    }

}
