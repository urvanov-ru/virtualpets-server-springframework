package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.shared.domain.CollectObjectArg;
import ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.shared.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.HiddenObjectsService;

@RestController
@RequestMapping(value = "rest/v1/HiddenObjectsService")
public class HiddenObjectsController {
    @Autowired
    private HiddenObjectsService hiddenObjectsService;
    
    @PostMapping("joinGame")
    public HiddenObjectsGame joinGame(@RequestBody JoinHiddenObjectsGameArg joinHiddenObjectsGameArg) throws DaoException, ServiceException {
        return hiddenObjectsService.joinGame(joinHiddenObjectsGameArg);
    }
    
    @GetMapping("getGameInfo")
    public HiddenObjectsGame getGameInfo() throws DaoException, ServiceException {
        return hiddenObjectsService.getGameInfo();
    }
    
    @PostMapping("collectObject")
    public HiddenObjectsGame collectObject(@RequestBody CollectObjectArg collectObjectArg) throws DaoException, ServiceException {
        return hiddenObjectsService.collectObject(collectObjectArg);
    }
    
    @PostMapping("startGame")
    public HiddenObjectsGame startGame() throws DaoException, ServiceException {
        return hiddenObjectsService.startGame();
    }
    
    @PostMapping("leaveGame")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveGame() throws DaoException, ServiceException {
        hiddenObjectsService.leaveGame();
    }
}
