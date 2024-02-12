/**
 * 
 */
package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.CollectObjectArg;
import ru.urvanov.virtualpets.shared.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.shared.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

/**
 * @author fedya
 *
 */
public interface HiddenObjectsService {
    public HiddenObjectsGame joinGame(JoinHiddenObjectsGameArg arg) throws DaoException, ServiceException;
    public HiddenObjectsGame getGameInfo() throws DaoException, ServiceException;
    public HiddenObjectsGame collectObject(CollectObjectArg arg) throws DaoException, ServiceException;
    public HiddenObjectsGame startGame() throws DaoException, ServiceException;
    public void leaveGame() throws DaoException, ServiceException;
}
