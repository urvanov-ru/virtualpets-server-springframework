package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.LoginArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.server.api.domain.UserInformation;
import ru.urvanov.virtualpets.server.api.domain.UserInformationArg;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface UserApiService {

    LoginResult login(LoginArg arg)
            throws ServiceException, DaoException;

    RefreshUsersOnlineResult getUsersOnline(UserDetailsImpl userDetails,
            SelectedPet selectedPet)
            throws ServiceException, DaoException;

    UserInformation getUserInformation(UserDetailsImpl userDetails,
            SelectedPet selectedPet, UserInformationArg argument)
            throws ServiceException, DaoException;

    void updateUserInformation(UserDetailsImpl userDetails,
            SelectedPet selectedPet, UserInformation arg)
            throws ServiceException, DaoException;

    void closeSession() throws DaoException, ServiceException;;
}
