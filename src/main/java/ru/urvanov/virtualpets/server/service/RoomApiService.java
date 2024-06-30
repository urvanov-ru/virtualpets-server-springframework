package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.server.api.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.server.api.domain.Point;
import ru.urvanov.virtualpets.server.api.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface RoomApiService {
    GetRoomInfoResult getRoomInfo(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void buildRefrigerator(UserDetailsImpl userDetails,
            SelectedPet selectedPet, Point position)
            throws DaoException, ServiceException;

    void moveRefrigerator(UserDetailsImpl userDetails,
            SelectedPet selectedPet, Point position)
            throws DaoException, ServiceException;

    void upgradeRefrigerator(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    public OpenBoxNewbieResult openBoxNewbie(UserDetailsImpl userDetails,
            SelectedPet selectedPet, int index)
            throws DaoException, ServiceException;

    void buildBookcase(UserDetailsImpl userDetails,
            SelectedPet selectedPet, Point position)
            throws DaoException, ServiceException;

    void upgradeBookcase(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void moveBookcase(UserDetailsImpl userDetails,
            SelectedPet selectedPet, Point position)
            throws DaoException, ServiceException;

    void buildMachineWithDrinks(UserDetailsImpl userDetails,
            SelectedPet selectedPet, Point position)
            throws DaoException, ServiceException;

    void moveMachineWithDrinks(UserDetailsImpl userDetails,
            SelectedPet selectedPet, Point position)
            throws DaoException, ServiceException;

    RoomBuildMenuCosts getBuildMenuCosts(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void upgradeMachineWithDrinks(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void pickJournalOnFloor(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void journalClosed() throws DaoException, ServiceException;
}
