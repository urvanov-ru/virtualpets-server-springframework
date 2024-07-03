package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.server.api.domain.CollectObjectArg;
import ru.urvanov.virtualpets.server.api.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.api.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.HiddenObjectsApiService;
import ru.urvanov.virtualpets.server.service.domain.HiddenObjectsGameStatus;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "rest/v1/HiddenObjectsService")
public class HiddenObjectsController {
    
    @Autowired
    private HiddenObjectsApiService hiddenObjectsService;

    @Autowired
    private HiddenObjectsGameStatus hiddenObjectsGameStatus;

    @Autowired
    private UserPetDetails userPetDetails;

    @PostMapping("joinGame")
    public HiddenObjectsGame joinGame(
            @RequestBody JoinHiddenObjectsGameArg joinHiddenObjectsGameArg)
            throws DaoException, ServiceException {
        return hiddenObjectsService.joinGame(userPetDetails,
                hiddenObjectsGameStatus, joinHiddenObjectsGameArg);
    }

    @GetMapping("getGameInfo")
    public HiddenObjectsGame getGameInfo()
            throws DaoException, ServiceException {
        return hiddenObjectsService.getGameInfo(userPetDetails,
                hiddenObjectsGameStatus);
    }

    @PostMapping("collectObject")
    public HiddenObjectsGame collectObject(
            @RequestBody CollectObjectArg collectObjectArg)
            throws DaoException, ServiceException {
        return hiddenObjectsService.collectObject(userPetDetails,
                hiddenObjectsGameStatus, collectObjectArg);
    }

    @PostMapping("startGame")
    public HiddenObjectsGame startGame()
            throws DaoException, ServiceException {
        return hiddenObjectsService.startGame(userPetDetails,
                hiddenObjectsGameStatus);
    }

    @PostMapping("leaveGame")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveGame() throws DaoException, ServiceException {
        hiddenObjectsService.leaveGame(userPetDetails,
                hiddenObjectsGameStatus);
    }
}
