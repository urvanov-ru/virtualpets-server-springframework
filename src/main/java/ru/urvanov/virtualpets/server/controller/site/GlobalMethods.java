package ru.urvanov.virtualpets.server.controller.site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalMethods {
    
    @ModelAttribute("menu_play_url")
    public String menuPlayUrl(@Value("${virtualpets-server-springframework.play.url}") String playUrl) {
        return playUrl;
    }

}
