package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.game.domain.CollectObjectArg;
import ru.urvanov.virtualpets.server.controller.game.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.controller.game.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.server.service.domain.HiddenObjectsGameStatus;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface HiddenObjectsApiService {
    HiddenObjectsGame joinGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus,
            JoinHiddenObjectsGameArg joinHiddenObjectsGameArg)
            throws ServiceException;

    HiddenObjectsGame getGameInfo(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws ServiceException;

    HiddenObjectsGame collectObject(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus,
            CollectObjectArg collectObjectArg)
            throws ServiceException;

    HiddenObjectsGame startGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws ServiceException;

    void leaveGame(
            UserPetDetails userPetDetails,
            HiddenObjectsGameStatus hiddenObjectsGameStatus)
            throws ServiceException;
}
