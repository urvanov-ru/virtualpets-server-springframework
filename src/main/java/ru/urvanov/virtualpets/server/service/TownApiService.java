package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface TownApiService {
    GetTownInfoResult getTownInfo(UserPetDetails userPetDetails)
            throws DaoException, ServiceException;
}
