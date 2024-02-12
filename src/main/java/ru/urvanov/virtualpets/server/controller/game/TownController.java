package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.shared.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.TownService;

@RestController
@RequestMapping(value = "rest/v1/TownService")
public class TownController {
    @Autowired
    private TownService townService;
    
    @GetMapping(value = "getTownInfo")
    public GetTownInfoResult getTownInfo() throws DaoException, ServiceException {
        return townService.getTownInfo();
    }
}
