/**
 * 
 */
package ru.urvanov.virtualpets.server.controller.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import ru.urvanov.virtualpets.server.controller.site.domain.StatisticsParams;
import ru.urvanov.virtualpets.server.controller.site.domain.StatisticsParams.StatisticsType;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.PetService;
import ru.urvanov.virtualpets.server.service.UserService;

/**
 * @author fedya
 * 
 */
@Controller
@RequestMapping("site")
public class StatisticsController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @RequestMapping(value = "/information/statistics", method = RequestMethod.GET)
    public String showStatistics(Locale locale, Model model) {
        StatisticsParams statisticsParams = new StatisticsParams();
        statisticsParams.setMaxRecordsCount(100);
        statisticsParams.setType(StatisticsType.LAST_REGISTERED_USERS);
        model.addAttribute("statisticsParams", statisticsParams);
        return "information/statistics";
    }

    @RequestMapping(value = "/information/statistics", method = RequestMethod.POST)
    public String showStatistics(Locale locale, Model model,
            @Valid @ModelAttribute StatisticsParams statisticsParams,
            BindingResult statisticsParamsBindingResult) {

        List<User> users = new ArrayList<User>();
        List<Pet> pets = new ArrayList<Pet>();
        if (!statisticsParamsBindingResult.hasErrors()) {
            switch (statisticsParams.getType()) {
            case LAST_REGISTERED_USERS:
                users = userService.findLastRegisteredUsers(0,
                        statisticsParams.getMaxRecordsCount());
                break;
            case LAST_CREATED_PETS:
                pets = petService.findLastCreatedPets(0,
                        statisticsParams.getMaxRecordsCount());
            }
        }
        model.addAttribute("users", users);
        model.addAttribute("pets", pets);
        return "information/statistics";
    }
}
