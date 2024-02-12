/**
 * 
 */
package ru.urvanov.virtualpets.server.controller.site;

import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fedya
 * 
 */
@Controller
@RequestMapping("site")
public class SigninController {
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Locale locale, Model model,
            @RequestParam(value = "unid") String uniqueIdentifier,
            @RequestParam(value = "type") String type,
            HttpServletRequest request) {
        request.getSession().setAttribute("unid", uniqueIdentifier);
        request.getSession().setAttribute("socialtype", type);
        model.addAttribute("type", type);
        return "signin";
    }
}
