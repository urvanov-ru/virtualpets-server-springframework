package ru.urvanov.virtualpets.server.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.urvanov.virtualpets.server.controller.api.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.server.controller.api.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.server.controller.api.domain.Point;
import ru.urvanov.virtualpets.server.controller.api.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.server.service.RoomApiService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "api/v1/RoomService")
public class RoomController extends ControllerBase {

    @Autowired
    private RoomApiService roomService;

    @Autowired
    private UserPetDetails userPetDetails;

    @GetMapping(value = "getRoomInfo")
    public GetRoomInfoResult getRoomInfo()
            throws ServiceException {
        return roomService.getRoomInfo(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "pickJournalOnFloor")
    public void pickJournalOnfloor()
            throws ServiceException {
        roomService.pickJournalOnFloor(userPetDetails);
    }

    @PostMapping(value = "getBuildMenuCosts")
    public RoomBuildMenuCosts getBuildMenuCosts()
            throws ServiceException {
        return roomService.getBuildMenuCosts(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "journalClosed")
    public void journalClosed() throws ServiceException {
        roomService.journalClosed(userPetDetails);
    }

    @PostMapping(value = "openBoxNewbie/{index}/")
    public OpenBoxNewbieResult openBoxNewbie(
            @PathVariable("index") int index)
            throws ServiceException {
        return roomService.openBoxNewbie(userPetDetails, index);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildMachineWithDrinks")
    public void buildMachineWithDrinks(
            @RequestBody @Valid Point position)
            throws ServiceException {
        roomService.buildMachineWithDrinks(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveMachineWithDrinks")
    public void moveMachineWithDrinks(
            @RequestBody @Valid  Point position)
            throws ServiceException {
        roomService.moveMachineWithDrinks(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildRefrigerator")
    public void buildRefrigerator(
            @RequestBody @Valid Point position)
            throws ServiceException {
        roomService.buildRefrigerator(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveRefrigerator")
    public void moveRefrigerator(
            @RequestBody @Valid Point position)
            throws ServiceException {
        roomService.moveRefrigerator(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildBookcase")
    public void buildBookcase(
            @RequestBody @Valid Point position)
            throws ServiceException {
        roomService.buildBookcase(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveBookcase")
    public void moveBookcase(
            @RequestBody @Valid Point position)
            throws ServiceException {
        roomService.moveBookcase(userPetDetails, position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeRefrigerator")
    public void upgradeRefrigerator()
            throws ServiceException {
        roomService.upgradeRefrigerator(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeBookcase")
    public void upgradeBookcase() throws ServiceException {
        roomService.upgradeBookcase(userPetDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeMachineWithDrinks")
    public void upgradeMachineWithDrinks()
            throws ServiceException {
        roomService.upgradeMachineWithDrinks(userPetDetails);
    }

}
