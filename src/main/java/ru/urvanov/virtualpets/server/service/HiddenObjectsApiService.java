package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.CollectObjectArg;
import ru.urvanov.virtualpets.server.api.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.api.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface HiddenObjectsApiService {
    HiddenObjectsGame joinGame(JoinHiddenObjectsGameArg arg)
            throws DaoException, ServiceException;

    HiddenObjectsGame getGameInfo()
            throws DaoException, ServiceException;

    HiddenObjectsGame collectObject(CollectObjectArg arg)
            throws DaoException, ServiceException;

    HiddenObjectsGame startGame() throws DaoException, ServiceException;

    void leaveGame() throws DaoException, ServiceException;
}
