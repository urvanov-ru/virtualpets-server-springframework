package ru.urvanov.virtualpets.server.controller.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Min;
import ru.urvanov.virtualpets.server.service.PetService;
import ru.urvanov.virtualpets.server.service.domain.PetDetails;
import ru.urvanov.virtualpets.server.service.exception.PetNotFoundException;

@Controller
@RequestMapping("site")
public class PetController extends ControllerBase {

    private static final Logger logger = LoggerFactory
            .getLogger(PetController.class);

    @Autowired
    private PetService petService;

    @RequestMapping(value = "/information/pet", method = RequestMethod.GET)
    public String petInfo(@RequestParam(value = "id") @Min(1) Integer id,
            Model model) throws PetNotFoundException {
        logger.info("/information/pet started.");
        PetDetails pet = petService.petInformationPage(id);
        model.addAttribute("pet", pet);
        logger.info("/information/pet finished.");
        return "information/pet";
    }

}
