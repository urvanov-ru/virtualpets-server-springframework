package ru.urvanov.virtualpets.server.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.urvanov.virtualpets.server.controller.api.domain.CollectObjectArg;
import ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.controller.api.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.server.service.HiddenObjectsGameService;
import ru.urvanov.virtualpets.server.service.domain.HiddenObjectsGameStatus;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "api/v1/HiddenObjectsService")
public class HiddenObjectsController extends ControllerBase {
    
    @Autowired
    private HiddenObjectsGameService hiddenObjectsService;

    @Autowired
    private HiddenObjectsGameStatus hiddenObjectsGameStatus;

    @Autowired
    private UserPetDetails userPetDetails;

    @PostMapping("joinGame")
    public HiddenObjectsGame joinGame(
            @RequestBody @Valid
            JoinHiddenObjectsGameArg joinHiddenObjectsGameArg)
            throws ServiceException {
        return hiddenObjectsService.joinGame(userPetDetails,
                hiddenObjectsGameStatus, joinHiddenObjectsGameArg);
    }

    @GetMapping("getGameInfo")
    public HiddenObjectsGame getGameInfo()
            throws ServiceException {
        return hiddenObjectsService.getGameInfo(userPetDetails,
                hiddenObjectsGameStatus);
    }

    @PostMapping("collectObject")
    public HiddenObjectsGame collectObject(
            @RequestBody @Valid CollectObjectArg collectObjectArg)
            throws ServiceException {
        return hiddenObjectsService.collectObject(userPetDetails,
                hiddenObjectsGameStatus, collectObjectArg);
    }

    @PostMapping("startGame")
    public HiddenObjectsGame startGame()
            throws ServiceException {
        return hiddenObjectsService.startGame(userPetDetails,
                hiddenObjectsGameStatus);
    }

    @PostMapping("leaveGame")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveGame() throws ServiceException {
        hiddenObjectsService.leaveGame(userPetDetails,
                hiddenObjectsGameStatus);
    }
}
