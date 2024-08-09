package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.server.controller.game.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.server.service.TownGameService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "game/v1TownService")
public class TownController extends ControllerBase {
    @Autowired
    private TownGameService townService;
    
    @Autowired
    private UserPetDetails userPetDetails;
    
    @GetMapping(value = "getTownInfo")
    public GetTownInfoResult getTownInfo() throws ServiceException {
        return townService.getTownInfo(userPetDetails);
    }
}
