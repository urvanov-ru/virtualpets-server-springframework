package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.api.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface TownApiService {
    GetTownInfoResult getTownInfo(UserPetDetails userPetDetails)
            throws ServiceException;
}
