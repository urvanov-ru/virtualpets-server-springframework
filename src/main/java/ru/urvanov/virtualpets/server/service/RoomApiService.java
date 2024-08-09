package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.game.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.server.controller.game.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.server.controller.game.domain.Point;
import ru.urvanov.virtualpets.server.controller.game.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface RoomApiService {
    GetRoomInfoResult getRoomInfo(UserPetDetails userPetDetails)
            throws ServiceException;

    void buildRefrigerator(UserPetDetails userPetDetails, Point position)
            throws ServiceException;

    void moveRefrigerator(UserPetDetails userPetDetails, Point position)
            throws ServiceException;

    void upgradeRefrigerator(UserPetDetails userPetDetails)
            throws ServiceException;

    public OpenBoxNewbieResult openBoxNewbie(
            UserPetDetails userPetDetails, int index)
            throws ServiceException;

    void buildBookcase(UserPetDetails userPetDetails, Point position)
            throws ServiceException;

    void upgradeBookcase(UserPetDetails userPetDetails)
            throws ServiceException;

    void moveBookcase(UserPetDetails userPetDetails, Point position)
            throws ServiceException;

    void buildMachineWithDrinks(UserPetDetails userPetDetails,
            Point position) throws ServiceException;

    void moveMachineWithDrinks(UserPetDetails userPetDetails,
            Point position) throws ServiceException;

    RoomBuildMenuCosts getBuildMenuCosts(UserPetDetails userPetDetails)
            throws ServiceException;

    void upgradeMachineWithDrinks(UserPetDetails userPetDetails)
            throws ServiceException;

    void pickJournalOnFloor(UserPetDetails userPetDetails)
            throws ServiceException;

    void journalClosed(UserPetDetails userPetDetails)
            throws ServiceException;
}
