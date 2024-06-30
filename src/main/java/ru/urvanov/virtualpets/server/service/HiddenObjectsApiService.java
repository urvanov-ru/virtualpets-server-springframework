package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.CollectObjectArg;
import ru.urvanov.virtualpets.server.api.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.api.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface HiddenObjectsApiService {
    HiddenObjectsGame joinGame(UserDetailsImpl userDetails,
            SelectedPet selectedPet,
            JoinHiddenObjectsGameArg joinHiddenObjectsGameArg)
            throws DaoException, ServiceException;

    HiddenObjectsGame getGameInfo(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    HiddenObjectsGame collectObject(UserDetailsImpl userDetails,
            SelectedPet selectedPet, CollectObjectArg collectObjectArg)
            throws DaoException, ServiceException;

    HiddenObjectsGame startGame(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws DaoException, ServiceException;

    void leaveGame(UserDetailsImpl userDetails, SelectedPet selectedPet)
            throws DaoException, ServiceException;
}
