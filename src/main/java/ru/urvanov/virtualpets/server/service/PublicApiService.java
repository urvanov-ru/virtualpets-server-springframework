package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.GetServersArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.api.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.server.api.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.api.domain.ServerInfo;
import ru.urvanov.virtualpets.server.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PublicApiService {
    ServerInfo[] getServers(GetServersArg getServersArgg)
            throws ServiceException;

    void register(RegisterArgument registerArgument)
            throws ServiceException;

    void recoverPassword(RecoverPasswordArg recoverPasswordArg)
            throws ServiceException;

    LoginResult recoverSession(RecoverSessionArg recoverASessionArg)
            throws ServiceException;

    ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException;
}
