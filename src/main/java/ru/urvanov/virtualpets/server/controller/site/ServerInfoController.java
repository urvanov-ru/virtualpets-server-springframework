package ru.urvanov.virtualpets.server.controller.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("site")
public class ServerInfoController extends ControllerBase {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);
    
    private static final String HTML_ESCAPE_TEST
            = "<H1><тест>экранирование</тест></H1>";

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

    @RequestMapping(value = "/information/serverInfo",
            method = RequestMethod.GET)
    public String serverInfo(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        String[] propertyNames = { "java.version", "java.vendor",
                "os.name", "os.arch", "os.version" };
        List<Info> properties = java.util.Arrays.stream(propertyNames)
                .map((key) -> new Info(key, System.getProperty(key)))
                .collect(Collectors.toList());
        List<Info> infos = new ArrayList<>();
        infos.addAll(properties);
        infos.add(new Info(HTML_ESCAPE_TEST, HTML_ESCAPE_TEST));
        model.addAttribute("infos", infos);

        return "information/serverInfo";
    }
}
