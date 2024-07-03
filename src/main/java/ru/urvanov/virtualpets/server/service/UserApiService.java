package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.LoginArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.server.api.domain.UserInformation;
import ru.urvanov.virtualpets.server.api.domain.UserInformationArg;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface UserApiService {

    LoginResult login(LoginArg loginArg)
            throws ServiceException;

    RefreshUsersOnlineResult getUsersOnline(
            UserPetDetails userPetDetails)
            throws ServiceException;

    UserInformation getUserInformation(UserPetDetails userPetDetails,
            UserInformationArg userInformationArg)
            throws ServiceException;

    void updateUserInformation(UserPetDetails userPetDetails,
            UserInformation userInformationArg)
            throws ServiceException;

    void closeSession(UserPetDetails userPetDetails)
            throws ServiceException;;
}
