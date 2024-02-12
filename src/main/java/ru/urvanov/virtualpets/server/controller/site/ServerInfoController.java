/**
 * 
 */
package ru.urvanov.virtualpets.server.controller.site;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fedya
 *
 */
@Controller
@RequestMapping("site")
public class ServerInfoController {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    public class Info {
        String key;
        String value;

        public Info(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    @RequestMapping(value = "/information/serverInfo", method = RequestMethod.GET)
    public String home(Locale locale, Model model, HttpServletRequest request) {
        logger.info("Welcome home! The client locale is {}.", locale);

        String[] propertyNames = { "java.version", "java.vendor", "os.name",
                "os.arch", "os.version" };
        List<Info> infos = java.util.Arrays.stream(propertyNames)
                .map((key) -> new Info(key, System.getProperty(key)))
                .collect(Collectors.toList());
        model.addAttribute("infos", infos);

        return "information/serverInfo";
    }
}
