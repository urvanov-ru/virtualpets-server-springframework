/**
 * 
 */
package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.shared.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.shared.domain.Point;
import ru.urvanov.virtualpets.shared.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

/**
 * @author fedya
 *
 */
public interface RoomService {
    GetRoomInfoResult getRoomInfo() throws DaoException, ServiceException;
    void buildRefrigerator(Point arg) throws DaoException, ServiceException;
    void moveRefrigerator(Point arg) throws DaoException, ServiceException;
    void upgradeRefrigerator() throws DaoException, ServiceException;
    public OpenBoxNewbieResult openBoxNewbie(int index) throws DaoException, ServiceException;
    void buildBookcase(Point arg) throws DaoException, ServiceException;
    void upgradeBookcase() throws DaoException, ServiceException;
    void moveBookcase(Point arg) throws DaoException, ServiceException;
    void buildMachineWithDrinks(Point arg) throws DaoException, ServiceException;
    void moveMachineWithDrinks(Point arg) throws DaoException, ServiceException;
    RoomBuildMenuCosts getBuildMenuCosts() throws DaoException, ServiceException;
    void upgradeMachineWithDrinks() throws DaoException, ServiceException;
    void pickJournalOnFloor() throws DaoException, ServiceException;
    void journalClosed() throws DaoException, ServiceException;
}
