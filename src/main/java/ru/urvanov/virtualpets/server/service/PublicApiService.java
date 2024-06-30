package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.api.domain.GetServersArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.api.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.server.api.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.api.domain.ServerInfo;
import ru.urvanov.virtualpets.server.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PublicApiService {
    ServerInfo[] getServers(GetServersArg arg)
            throws ServiceException, DaoException;

    void register(RegisterArgument arg)
            throws ServiceException, DaoException;

    void recoverPassword(RecoverPasswordArg argument)
            throws ServiceException, DaoException;

    LoginResult recoverSession(RecoverSessionArg arg)
            throws ServiceException, DaoException;

    ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException, DaoException;
}
