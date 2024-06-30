package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.CollectObjectArg;
import ru.urvanov.virtualpets.server.api.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.api.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.HiddenObjectsGameStatus;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface HiddenObjectsApiService {
    HiddenObjectsGame joinGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus,
            JoinHiddenObjectsGameArg joinHiddenObjectsGameArg)
            throws DaoException, ServiceException;

    HiddenObjectsGame getGameInfo(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws DaoException, ServiceException;

    HiddenObjectsGame collectObject(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus,
            CollectObjectArg collectObjectArg)
            throws DaoException, ServiceException;

    HiddenObjectsGame startGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws DaoException, ServiceException;

    void leaveGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws DaoException, ServiceException;
}
