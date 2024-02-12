package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.shared.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.shared.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.shared.domain.Point;
import ru.urvanov.virtualpets.shared.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.RoomService;

@RestController
@RequestMapping(value = "rest/v1/RoomService")
public class RoomController {
    
    @Autowired
    private RoomService roomService;
    
    @GetMapping(value = "getRoomInfo")
    public GetRoomInfoResult getRoomInfo() throws DaoException, ServiceException {
        return roomService.getRoomInfo();
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "pickJournalOnFloor")
    public void pickJournalOnfloor() throws DaoException, ServiceException {
        roomService.pickJournalOnFloor();
    }
    
    @PostMapping(value = "getBuildMenuCosts")
    public RoomBuildMenuCosts getBuildMenuCosts() throws DaoException, ServiceException {
        return roomService.getBuildMenuCosts();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "journalClosed")
    public void journalClosed() throws DaoException, ServiceException {
        roomService.journalClosed();
    }
    
    @PostMapping(value = "openBoxNewbie/{index}/")
    public OpenBoxNewbieResult openBoxNewbie(@PathVariable("index") int index) throws DaoException, ServiceException {
        return roomService.openBoxNewbie(index);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildMachineWithDrinks") 
    public void buildMachineWithDrinks(@RequestBody Point point) throws DaoException, ServiceException {
        roomService.buildMachineWithDrinks(point);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveMachineWithDrinks")
    public void moveMachineWithDrinks(@RequestBody Point point) throws DaoException, ServiceException {
        roomService.moveMachineWithDrinks(point);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildRefrigerator") 
    public void buildRefrigerator(@RequestBody Point point) throws DaoException, ServiceException {
        roomService.buildRefrigerator(point);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveRefrigerator")
    public void moveRefrigerator(@RequestBody Point point) throws DaoException, ServiceException {
        roomService.moveRefrigerator(point);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildBookcase") 
    public void buildBookcase(@RequestBody Point point) throws DaoException, ServiceException {
        roomService.buildBookcase(point);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveBookcase")
    public void moveBookcase(@RequestBody Point point) throws DaoException, ServiceException {
        roomService.moveBookcase(point);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeRefrigerator")
    public void upgradeRefrigerator() throws DaoException, ServiceException {
        roomService.upgradeRefrigerator();
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeBookcase")
    public void upgradeBookcase() throws DaoException, ServiceException {
        roomService.upgradeBookcase();
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeMachineWithDrinks")
    public void upgradeMachineWithDrinks() throws DaoException, ServiceException {
        roomService.upgradeMachineWithDrinks();
    }

}
