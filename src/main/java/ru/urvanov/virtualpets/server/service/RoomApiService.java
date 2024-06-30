package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.server.api.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.server.api.domain.Point;
import ru.urvanov.virtualpets.server.api.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface RoomApiService {
    GetRoomInfoResult getRoomInfo(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void buildRefrigerator(UserPetDetails userPetDetails, Point position)
            throws DaoException, ServiceException;

    void moveRefrigerator(UserPetDetails userPetDetails, Point position)
            throws DaoException, ServiceException;

    void upgradeRefrigerator(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    public OpenBoxNewbieResult openBoxNewbie(
            UserPetDetails userPetDetails, int index)
            throws DaoException, ServiceException;

    void buildBookcase(UserPetDetails userPetDetails, Point position)
            throws DaoException, ServiceException;

    void upgradeBookcase(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void moveBookcase(UserPetDetails userPetDetails, Point position)
            throws DaoException, ServiceException;

    void buildMachineWithDrinks(UserPetDetails userPetDetails,
            Point position) throws DaoException, ServiceException;

    void moveMachineWithDrinks(UserPetDetails userPetDetails,
            Point position) throws DaoException, ServiceException;

    RoomBuildMenuCosts getBuildMenuCosts(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void upgradeMachineWithDrinks(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void pickJournalOnFloor(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;

    void journalClosed(UserPetDetails userPetDetails) throws DaoException, ServiceException;
}
