package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.server.api.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.server.api.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.server.api.domain.Point;
import ru.urvanov.virtualpets.server.api.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.RoomApiService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "rest/v1/RoomService")
public class RoomController {

    @Autowired
    private RoomApiService roomService;

    @Autowired
    private UserPetDetails userPetDetails;

    @GetMapping(value = "getRoomInfo")
    public GetRoomInfoResult getRoomInfo()
            throws DaoException, ServiceException {
        return roomService.getRoomInfo(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "pickJournalOnFloor")
    public void pickJournalOnfloor()
            throws DaoException, ServiceException {
        roomService.pickJournalOnFloor(userPetDetails);
    }

    @PostMapping(value = "getBuildMenuCosts")
    public RoomBuildMenuCosts getBuildMenuCosts()
            throws DaoException, ServiceException {
        return roomService.getBuildMenuCosts(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "journalClosed")
    public void journalClosed() throws DaoException, ServiceException {
        roomService.journalClosed(userPetDetails);
    }

    @PostMapping(value = "openBoxNewbie/{index}/")
    public OpenBoxNewbieResult openBoxNewbie(
            @PathVariable("index") int index)
            throws DaoException, ServiceException {
        return roomService.openBoxNewbie(userPetDetails, index);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildMachineWithDrinks")
    public void buildMachineWithDrinks(@RequestBody Point position)
            throws DaoException, ServiceException {
        roomService.buildMachineWithDrinks(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveMachineWithDrinks")
    public void moveMachineWithDrinks(@RequestBody Point position)
            throws DaoException, ServiceException {
        roomService.moveMachineWithDrinks(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildRefrigerator")
    public void buildRefrigerator(@RequestBody Point position)
            throws DaoException, ServiceException {
        roomService.buildRefrigerator(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveRefrigerator")
    public void moveRefrigerator(@RequestBody Point position)
            throws DaoException, ServiceException {
        roomService.moveRefrigerator(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildBookcase")
    public void buildBookcase(@RequestBody Point position)
            throws DaoException, ServiceException {
        roomService.buildBookcase(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveBookcase")
    public void moveBookcase(@RequestBody Point position)
            throws DaoException, ServiceException {
        roomService.moveBookcase(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeRefrigerator")
    public void upgradeRefrigerator()
            throws DaoException, ServiceException {
        roomService.upgradeRefrigerator(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeBookcase")
    public void upgradeBookcase() throws DaoException, ServiceException {
        roomService.upgradeBookcase(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeMachineWithDrinks")
    public void upgradeMachineWithDrinks()
            throws DaoException, ServiceException {
        roomService.upgradeMachineWithDrinks(userPetDetails);
    }

}
