package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.game.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface TownGameService {
    GetTownInfoResult getTownInfo(UserPetDetails userPetDetails)
            throws ServiceException;
}
